package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.dto.AuthorDto;
import com.benitomiyazato.ecommercelivros.dto.GenderDto;
import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import com.benitomiyazato.ecommercelivros.service.AuthorService;
import com.benitomiyazato.ecommercelivros.service.BookService;
import com.benitomiyazato.ecommercelivros.service.GenderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView saveBook() {
        ModelAndView mv = new ModelAndView("/admin/books/registration");
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

        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);

        return new ModelAndView("redirect:/admin/authors");
    }

    @GetMapping("/authors/delete/{id}")
    public ModelAndView deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthorById(id);
        return new ModelAndView("redirect:/admin/authors");
    }

    @GetMapping("/authors/update/{id}")
    public ModelAndView updateAuthor(@PathVariable("id") Long id){
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if(authorOptional.isEmpty())
            return new ModelAndView("/error/404");

        Author author = authorOptional.get();
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author, authorDto);

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

        Gender gender = new Gender();
        BeanUtils.copyProperties(genderDto, gender);

        genderService.saveNewGender(gender);
        return new ModelAndView("redirect:/admin/genders");
    }

    @GetMapping("/genders/update/{id}")
    public ModelAndView updateGender(@PathVariable("id") Long id){
        Optional<Gender> genderOptional = genderService.findGenderById(id);
        if(genderOptional.isEmpty())
            return new ModelAndView("/error/404");

        Gender gender = genderOptional.get();
        GenderDto genderDto = new GenderDto();
        BeanUtils.copyProperties(gender, genderDto);

        ModelAndView mv = new ModelAndView("/admin/genders/registration");
        mv.addObject("genderDto", genderDto);
        return mv;
    }
}
