package com.benitomiyazato.ecommercelivros.controller;

import com.benitomiyazato.ecommercelivros.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ModelAndView fetchBookList(){
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("bookList", bookService.fetchBookList());
        return mv;
    }
}
