package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.dto.*;
import com.benitomiyazato.ecommercelivros.model.Collection;
import com.benitomiyazato.ecommercelivros.model.*;
import com.benitomiyazato.ecommercelivros.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private DiscountService discountService;

    private final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("/admin/index");
    }

    @GetMapping("/books")
    public ModelAndView fetchAllBooks() {
        ModelAndView mv = new ModelAndView("/admin/books/list");
        List<Book> bookList = bookService.fetchBookList();
        mv.addObject("bookList", bookList);
        return mv;
    }

    @GetMapping("/books/details/{id}")
    public ModelAndView fetchBookDetails(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/books/details");
        Optional<Book> bookOptional = bookService.findBookById(id);
        if (bookOptional.isEmpty()) {
            return new ModelAndView("/error/404");
        }

        mv.addObject("book", bookOptional.get());
        return mv;
    }

    @GetMapping("/books/registration")
    public ModelAndView bookRegistrationPage() {
        ModelAndView mv = new ModelAndView("/admin/books/registration");
        mv.addObject("bookDto", new BookDto());
        mv.addObject("authorList", authorService.fetchAuthorList());
        mv.addObject("genderList", genderService.fetchGenderList());
        return mv;
    }

    @PostMapping("/books/registration")
    public ModelAndView saveNewBook(@Valid BookDto bookDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("/admin/books/registration");
            mv.addObject("bookDto", new BookDto());
            mv.addObject("authorList", authorService.fetchAuthorList());
            mv.addObject("genderList", genderService.fetchGenderList());
            return mv;
        }

        if (bookService.existsByTitle(bookDto.getTitle()) && !bookDto.isEditing()) {
            ModelAndView mv = new ModelAndView("/admin/books/registration");
            mv.addObject("bookDto", new BookDto());
            mv.addObject("authorList", authorService.fetchAuthorList());
            mv.addObject("genderList", genderService.fetchGenderList());
            mv.addObject("duplicateTitleError", "Já existe um livro com este título no sistema.");
            return mv;
        }

        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);

        // setting all book's genders
        book.setAuthor(authorService.findAuthorById(bookDto.getAuthorId()).get());

        List<Gender> genders = new ArrayList<>();
        for (Long genderId : bookDto.getGenderIds()) {
            genders.add(genderService.findGenderById(genderId).get());
        }
        book.setGenders(genders);

        // deletes old images when editing a book
        if (bookDto.isEditing()) {
            book.setFileName1("");
            book.setFileName2("");
            book.setFileName3("");

            Optional<Book> bookOptional = bookService.findBookById(bookDto.getBookId());
            if (bookOptional.isPresent()) {
                Book fetchedBook = bookOptional.get();
                Path path = Paths.get(UPLOAD_DIRECTORY + "\\books\\" + fetchedBook.getTitle());
                try {
                    FileUtils.deleteDirectory(path.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // saving all book's images
        MultipartFile image1 = bookDto.getImage1();
        MultipartFile image2 = bookDto.getImage2();
        MultipartFile image3 = bookDto.getImage3();
        Path image1Path;
        Path image2Path;
        Path image3Path;

        final String UPLOAD_DIRECTORY_BOOK_FOLDER = UPLOAD_DIRECTORY + "\\books\\" + bookDto.getTitle();
        try {
            image1Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\1-" + image1.getOriginalFilename());
            Files.createDirectories(Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER));
            Files.write(image1Path, image1.getBytes());
            book.setFileName1("1-" + image1.getOriginalFilename());

            if (!image2.isEmpty()) {
                image2Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\2-" + image2.getOriginalFilename());
                Files.write(image2Path, image2.getBytes());
                book.setFileName2("2-" + image2.getOriginalFilename());
            }

            if (!image3.isEmpty()) {
                image3Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\3-" + image3.getOriginalFilename());
                Files.write(image3Path, image3.getBytes());
                book.setFileName3("3-" + image3.getOriginalFilename());
            }
        } catch (IOException e) {
            ModelAndView mv = new ModelAndView("/admin/books/registration");
            mv.addObject("bookDto", new BookDto());
            mv.addObject("authorList", authorService.fetchAuthorList());
            mv.addObject("genderList", genderService.fetchGenderList());
            mv.addObject("imageUploadError", "Ocorreu um erro no upload das imagens, por favor tente novamente.");
            return mv;
        }

        bookService.saveNewBook(book);
        return new ModelAndView("redirect:/admin/books");
    }

    @GetMapping("/books/delete/{id}")
    public ModelAndView deleteBook(@PathVariable("id") Long id) {
        Optional<Book> bookOptional = bookService.findBookById(id);
        if (bookOptional.isEmpty())
            return new ModelAndView("/error/404");

        Book book = bookOptional.get();

        // deletes book's collections
        if (!book.getCollections().isEmpty()) {
            List<Collection> collections = book.getCollections();
            collections.stream().forEach(x -> collectionService.deleteById(x.getCollectionId()));
        }

        // deletes book's images
        Path path = Paths.get(UPLOAD_DIRECTORY + "\\books\\" + book.getTitle());
        try {
            FileUtils.deleteDirectory(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // deletes book's discount
        if (book.atDiscount())
            discountService.delete(book.getDiscount());


        bookService.deleteBookById(id);
        return new ModelAndView("redirect:/admin/books");
    }

    @GetMapping("/books/update/{id}")
    public ModelAndView updateBook(@PathVariable("id") Long id) {
        Optional<Book> bookOptional = bookService.findBookById(id);
        if (bookOptional.isEmpty())
            return new ModelAndView("/error/404");

        Book book = bookOptional.get();
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);

        bookDto.setAuthorId(authorService.findAuthorById(book.getAuthor().getAuthorId()).get().getAuthorId());
        bookDto.setEditing(true);

        ModelAndView mv = new ModelAndView("/admin/books/registration");
        mv.addObject("bookDto", bookDto);
        mv.addObject("authorList", authorService.fetchAuthorList());
        mv.addObject("genderList", genderService.fetchGenderList());
        return mv;
    }

    @GetMapping("/authors")
    public ModelAndView fetchAllAuthors() {
        ModelAndView mv = new ModelAndView("/admin/authors/list");
        List<Author> authorList = authorService.fetchAuthorList();
        mv.addObject("authorList", authorList);
        return mv;
    }

    @GetMapping("/authors/details/{id}")
    public ModelAndView fetchAuthorDetails(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/authors/details");
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if (authorOptional.isEmpty()) {
            return new ModelAndView("/error/404");
        }

        mv.addObject("author", authorOptional.get());
        mv.addObject("bookList", bookService.fetchBookListOfAuthorById(id));
        return mv;
    }

    @GetMapping("/authors/registration")
    public ModelAndView authorRegistrationPage() {
        ModelAndView mv = new ModelAndView("/admin/authors/registration");
        mv.addObject("authorDto", new AuthorDto());
        return mv;
    }

    @PostMapping("/authors/registration")
    public ModelAndView saveNewAuthor(@Valid AuthorDto authorDto, BindingResult result) {
        if (result.hasErrors())
            return new ModelAndView("/admin/authors/registration");

        if (authorService.existsByName(authorDto.getName()) && !authorDto.isEditing()) {
            ModelAndView mv = new ModelAndView("/admin/authors/registration");
            mv.addObject("duplicateNameError", "Já existe um autor com este nome no sistema.");
            return mv;
        }

        // deletes previous images if is editing
        if (authorDto.isEditing()) {
            Optional<Author> authorOptional = authorService.findAuthorById(authorDto.getAuthorId());
            if (authorOptional.isPresent()) {
                Author author = authorOptional.get();
                Path path = Paths.get(UPLOAD_DIRECTORY + "\\authors\\" + author.getFileName());
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MultipartFile image = authorDto.getImage();
        String fileName = authorDto.getName() + image.getOriginalFilename();
        Path destination = Paths.get(UPLOAD_DIRECTORY + "\\authors\\" + fileName);
        try {
            Files.write(destination, image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        author.setFileName(fileName);

        authorService.saveNewAuthor(author);

        return new ModelAndView("redirect:/admin/authors");
    }

    @GetMapping("/authors/delete/{id}")
    public ModelAndView deleteAuthor(@PathVariable("id") Long id) {
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            Path path = Paths.get(UPLOAD_DIRECTORY + "\\authors\\" + author.getFileName());
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        authorService.deleteAuthorById(id);
        return new ModelAndView("redirect:/admin/authors");
    }

    @GetMapping("/authors/update/{id}")
    public ModelAndView updateAuthor(@PathVariable("id") Long id) {
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if (authorOptional.isEmpty())
            return new ModelAndView("/error/404");

        Author author = authorOptional.get();
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author, authorDto);
        authorDto.setEditing(true);

        ModelAndView mv = new ModelAndView("/admin/authors/registration");
        mv.addObject("authorDto", authorDto);
        return mv;
    }

    @GetMapping("/genders")
    public ModelAndView fetchAllGenders() {
        ModelAndView mv = new ModelAndView("/admin/genders/list");
        List<Gender> genderList = genderService.fetchGenderList();
        mv.addObject("genderList", genderList);
        return mv;
    }

    @GetMapping("/genders/registration")
    public ModelAndView genderRegistrationPage() {
        ModelAndView mv = new ModelAndView("/admin/genders/registration");
        mv.addObject("genderDto", new GenderDto());
        return mv;
    }

    @PostMapping("/genders/registration")
    public ModelAndView saveNewGender(@Valid GenderDto genderDto, BindingResult result) {
        if (result.hasErrors())
            return new ModelAndView("/admin/genders/registration");

        if (genderService.existsByName(genderDto.getName()) && !genderDto.isEditing()) {
            ModelAndView mv = new ModelAndView("/admin/genders/registration");
            mv.addObject("duplicateNameError", "Já existe um gênero com esse nome no sistema.");
            return mv;
        }

        Gender gender = new Gender();
        BeanUtils.copyProperties(genderDto, gender);

        genderService.saveNewGender(gender);
        return new ModelAndView("redirect:/admin/genders");
    }

    @GetMapping("/genders/update/{id}")
    public ModelAndView updateGender(@PathVariable("id") Long id) {
        Optional<Gender> genderOptional = genderService.findGenderById(id);
        if (genderOptional.isEmpty())
            return new ModelAndView("/error/404");

        Gender gender = genderOptional.get();
        GenderDto genderDto = new GenderDto();
        BeanUtils.copyProperties(gender, genderDto);
        genderDto.setEditing(true);

        ModelAndView mv = new ModelAndView("/admin/genders/registration");
        mv.addObject("genderDto", genderDto);
        return mv;
    }

    @GetMapping("/genders/delete/{id}")
    public ModelAndView deleteGender(@PathVariable("id") Long id) {
        Optional<Gender> genderOptional = genderService.findGenderById(id);
        if (genderOptional.isPresent()) {
            Gender gender = genderOptional.get();
            gender.getBooks().stream().forEach(x -> bookService.deleteBookById(x.getBookId()));
        }

        genderService.deleteGenderById(id);
        return new ModelAndView("redirect:/admin/genders");
    }

    @GetMapping("/genders/details/{id}")
    public ModelAndView fetchGenderDetails(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/genders/details");
        Optional<Gender> genderOptional = genderService.findGenderById(id);
        if (genderOptional.isEmpty()) {
            return new ModelAndView("/error/404");
        }
        Gender gender = genderOptional.get();

        mv.addObject("gender", gender);
        mv.addObject("bookList", bookService.fetchBookListOfGender(gender));
        return mv;
    }

    @GetMapping("/collections")
    public ModelAndView fetchAllCollections() {
        ModelAndView mv = new ModelAndView("/admin/collections/list");
        List<Collection> collectionList = collectionService.fetchCollectionList();
        mv.addObject("collectionList", collectionList);
        return mv;
    }

    @GetMapping("/collections/registration")
    public ModelAndView collectionRegistrationPage() {
        ModelAndView mv = new ModelAndView("/admin/collections/registration");
        mv.addObject("collectionDto", new CollectionDto());
        mv.addObject("bookList", bookService.fetchBookList());
        return mv;
    }

    @PostMapping("/collections/registration")
    public ModelAndView saveNewCollection(@Valid CollectionDto collectionDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("/admin/collections/registration");
            mv.addObject("collectionDto", new CollectionDto());
            mv.addObject("bookList", bookService.fetchBookList());
            return mv;
        }

        if (collectionService.existsByTitle(collectionDto.getTitle()) && !collectionDto.isEditing()) {
            ModelAndView mv = new ModelAndView("/admin/books/registration");
            mv.addObject("bookDto", new BookDto());
            mv.addObject("bookList", bookService.fetchBookList());
            mv.addObject("duplicateTitleError", "Já existe uma coleção com este título no sistema.");
            return mv;
        }

        Collection collection = new Collection();
        BeanUtils.copyProperties(collectionDto, collection);


        // setting all collection's books
        System.out.println("COLLECTION REGISTRATION");
        System.out.println("collectionDto.getBookTitles() = " + collectionDto.getBookTitles());
        System.out.println("collection.getBooks() = " + collection.getBooks());
        List<Book> books = new ArrayList<>();
        for (String bookTitle : collectionDto.getBookTitles()) {
            Optional<Book> bookOptional = bookService.findBookByTitle(bookTitle);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                books.add(book);
            }
        }
        System.out.println("books = " + books);
        collection.setBooks(books);

        // saving all collection's images
        MultipartFile image1 = collectionDto.getImage1();
        MultipartFile image2 = collectionDto.getImage2();
        MultipartFile image3 = collectionDto.getImage3();
        Path image1Path;
        Path image2Path;
        Path image3Path;

        if (collectionDto.isUsingBookImages()) {
            List<Book> collectionBooks = collection.getBooks();
            collection.setFileName1(collectionBooks.get(0).getFileName1());
            collection.setFileName2(collectionBooks.get(1).getFileName1());

            if(collectionBooks.size() == 3)
                collection.setFileName3(collectionBooks.get(2).getFileName1());

        } else {
            final String UPLOAD_DIRECTORY_BOOK_FOLDER = UPLOAD_DIRECTORY + "\\collections\\" + collectionDto.getTitle();

            try {
                image1Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\1-" + image1.getOriginalFilename());
                Files.createDirectories(Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER));
                Files.write(image1Path, image1.getBytes());
                collection.setFileName1("1-" + image1.getOriginalFilename());

                if (!image2.isEmpty()) {
                    image2Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\2-" + image2.getOriginalFilename());
                    Files.write(image2Path, image2.getBytes());
                    collection.setFileName2("2-" + image2.getOriginalFilename());
                }

                if (!image3.isEmpty()) {
                    image3Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\3-" + image3.getOriginalFilename());
                    Files.write(image3Path, image3.getBytes());
                    collection.setFileName3("3-" + image3.getOriginalFilename());
                }
            } catch (IOException e) {
                ModelAndView mv = new ModelAndView("/admin/books/registration");
                mv.addObject("collectionDto", new CollectionDto());
                mv.addObject("bookList", bookService.fetchBookList());
                mv.addObject("imageUploadError", "Ocorreu um erro no upload das imagens, por favor tente novamente.");
                return mv;
            }
        }

        collectionService.saveNewCollection(collection);
        return new ModelAndView("redirect:/admin/collections");
    }

    @GetMapping("/collections/delete/{id}")
    public ModelAndView deleteCollection(@PathVariable("id") Long id) {
        Optional<Collection> collectionOptional = collectionService.findCollectionById(id);
        if (collectionOptional.isPresent()) {
            Collection collection = collectionOptional.get();
            if (!collection.isUsingBookImages()) {
                Path path = Paths.get(UPLOAD_DIRECTORY + "\\collections\\" + collection.getTitle());
                try {
                    FileUtils.deleteDirectory(path.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        collectionService.deleteCollectionById(id);
        return new ModelAndView("redirect:/admin/collections");
    }

    @GetMapping("/collections/update/{id}")
    public ModelAndView updateCollection(@PathVariable("id") Long id) {
        Optional<Collection> collectionOptional = collectionService.findCollectionById(id);
        if (collectionOptional.isEmpty())
            return new ModelAndView("/error/404");

        Collection collection = collectionOptional.get();
        CollectionDto collectionDto = new CollectionDto();
        BeanUtils.copyProperties(collection, collectionDto);
        collectionDto.setEditing(true);

        ModelAndView mv = new ModelAndView("/admin/collections/registration");
        mv.addObject("collectionDto", collectionDto);
        mv.addObject("bookList", bookService.fetchBookList());
        return mv;
    }

    @GetMapping("/collections/details/{id}")
    public ModelAndView fetchCollectionDetails(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/collections/details");
        Optional<Collection> collectionOptional = collectionService.findCollectionById(id);
        if (collectionOptional.isEmpty())
            return new ModelAndView("/error/404");

        // removing duplicates caused by a bug
        Collection collection = collectionOptional.get();
        List<Book> collectionBooks = collection.getBooks();
        Set<Book> bookSet = new HashSet<>(collectionBooks);
        collection.getBooks().clear();
        collection.getBooks().addAll(bookSet);

        mv.addObject("collection", collection);
        return mv;
    }

    @GetMapping("/discounts")
    public ModelAndView fetchAllDiscounts() {
        ModelAndView mv = new ModelAndView("/admin/discounts/list");
        mv.addObject("discountList", discountService.fetchAllDiscounts());
        return mv;
    }

    @GetMapping("/discounts/registration")
    public ModelAndView discountRegistrationPage() {
        ModelAndView mv = new ModelAndView("/admin/discounts/registration");
        mv.addObject("discountDto", new DiscountDto());
        mv.addObject("bookList", bookService.fetchBookList());
        return mv;
    }

    @GetMapping("/discounts/delete/{id}")
    public ModelAndView deleteDiscount(@PathVariable("id") Long id) {
        Optional<Discount> discountOptional = discountService.findById(id);
        if (discountOptional.isEmpty())
            return new ModelAndView("/error/404");

        Discount discountToDelete = discountOptional.get();
        discountService.delete(discountToDelete);
        return new ModelAndView("redirect:/admin/discounts");
    }

    @PostMapping("/discounts/registration")
    public ModelAndView saveNewDiscount(@Valid DiscountDto discountDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("/admin/discounts/registration");
            mv.addObject("discountDto", new DiscountDto());
            mv.addObject("bookList", bookService.fetchBookList());
            return mv;
        }

        Book discountBook = bookService.findBookById(discountDto.getBookId()).get();
        if (discountBook.atDiscount()) {
            ModelAndView mv = new ModelAndView("/admin/discounts/registration");
            mv.addObject("discountDto", new DiscountDto());
            mv.addObject("bookList", bookService.fetchBookList());
            mv.addObject("alreadyAtDiscount", "Este livro já está em desconto");
            return mv;
        }

        double amountOfDiscount = Precision.round(discountBook.getPrice() * (discountDto.getPercentageOfDiscount() / 100), 2);

        Discount discount = new Discount();
        BeanUtils.copyProperties(discountDto, discount);
        discount.setBook(discountBook);
        discount.setAmountOfDiscount(amountOfDiscount);

        discountService.saveNewDiscount(discount);
        return new ModelAndView("redirect:/admin/discounts");
    }
}
