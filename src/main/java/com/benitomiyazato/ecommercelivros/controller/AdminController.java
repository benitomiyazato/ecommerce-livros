package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.service.AuthorService;
import com.benitomiyazato.ecommercelivros.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/index");
        return mv;
    }

    @GetMapping("/books")
    public ModelAndView fetchAllBooks(){
        ModelAndView mv = new ModelAndView("/admin/books/list");
        List<Book> bookList = bookService.fetchBookList();
        mv.addObject("bookList", bookList);
        return mv;
    }

    @GetMapping("/books/registration")
    public ModelAndView saveBook(){
        ModelAndView mv = new ModelAndView("/admin/books/registration");
        return mv;
    }

    @GetMapping("/authors")
    public ModelAndView fetchAllAuthors(){
        ModelAndView mv = new ModelAndView("/admin/authors/list");
        List<Author> authorList = authorService.fetchAuthorList();
        mv.addObject("authorList", authorList);
        return mv;
    }

    @GetMapping("/authors/details/{id}")
    public ModelAndView fetchAuthor(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("/admin/authors/details");
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if(authorOptional.isEmpty()){
            return new ModelAndView("/error/404");
        }

        mv.addObject("author", authorOptional.get());
        return mv;
    }
}
