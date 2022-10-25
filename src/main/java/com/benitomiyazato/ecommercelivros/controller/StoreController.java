package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.model.Book;
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
@RequestMapping("/store")
public class StoreController {

    @Autowired
    BookService bookService;

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
}
