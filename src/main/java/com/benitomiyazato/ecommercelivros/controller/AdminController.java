package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.dto.AuthorDto;
import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.service.AuthorService;
import com.benitomiyazato.ecommercelivros.service.BookService;
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

        System.out.println("author = " + author);

        System.out.println("authorsaved = " + authorService.saveNewAuthor(author));
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
}
