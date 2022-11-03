package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.service.AuthorService;
import com.benitomiyazato.ecommercelivros.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @GetMapping
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/store/index");

        List<Book> bookList = bookService.fetchBookList();
        mv.addObject("bookList", bookList);
        return mv;
    }
    @GetMapping("/books/{id}")
    public ModelAndView fetchBookDetails(@PathVariable Long id){
        Optional<Book> bookOptional = bookService.findBookById(id);
        if(bookOptional.isEmpty())
            return new ModelAndView("/error/404");

        Book book = bookOptional.get();

        ModelAndView mv = new ModelAndView("/store/book");
        mv.addObject("book", book);
        return mv;
    }

    @GetMapping("/authors/{id}")
    public ModelAndView fetchAuthorDetails(@PathVariable Long id){
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if(authorOptional.isEmpty())
            return new ModelAndView("/error/404");

        ModelAndView mv = new ModelAndView("/store/author");
        Author author = authorOptional.get();

        List<Book> authorBooks = bookService.fetchBookListOfAuthor(author);

        mv.addObject("author", author);
        mv.addObject("authorBooks", authorBooks);
        return mv;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(name = "keyword") String keyword){
        ModelAndView mv = new ModelAndView("/store/search");

        List<Book> books = bookService.search(keyword);
        mv.addObject("books", books);
        mv.addObject("keyword", ("\"" + keyword + "\""));
        return mv;
    }
}
