package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.dto.AuthorDto;
import com.benitomiyazato.ecommercelivros.dto.BookDto;
import com.benitomiyazato.ecommercelivros.dto.GenderDto;
import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import com.benitomiyazato.ecommercelivros.service.AuthorService;
import com.benitomiyazato.ecommercelivros.service.BookService;
import com.benitomiyazato.ecommercelivros.service.GenderService;
import org.apache.commons.io.FileUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenderService genderService;

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


        // saving all book's images
        MultipartFile image1 = bookDto.getImage1();
        MultipartFile image2 = bookDto.getImage2();
        MultipartFile image3 = bookDto.getImage3();
        Path image1Path;
        Path image2Path;
        Path image3Path;

        final String FOLDER_NAME = bookDto.getTitle();
        final String UPLOAD_DIRECTORY_BOOK_FOLDER = UPLOAD_DIRECTORY + "\\books\\" + FOLDER_NAME;

        try {
            image1Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\1-" + image1.getOriginalFilename());
            Files.createDirectories(Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER));
            Files.write(image1Path, image1.getBytes());
            book.setImage1Path(FOLDER_NAME + "\\1-" + image1.getOriginalFilename());

            if (!image2.isEmpty()) {
                image2Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\2-" + image2.getOriginalFilename());
                Files.write(image2Path, image2.getBytes());
                book.setImage2Path(FOLDER_NAME + "\\2-" + image2.getOriginalFilename());
            }

            if (!image3.isEmpty()) {
                image3Path = Paths.get(UPLOAD_DIRECTORY_BOOK_FOLDER + "\\3-" + image3.getOriginalFilename());
                Files.write(image3Path, image3.getBytes());
                book.setImage3Path(FOLDER_NAME + "\\3-" + image3.getOriginalFilename());
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
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Path path = Paths.get(UPLOAD_DIRECTORY + "\\books\\" + book.getTitle());
            try {
                FileUtils.deleteDirectory(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
}
