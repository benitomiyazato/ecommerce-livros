package com.benitomiyazato.ecommercelivros.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/images")
public class ImagesController {

    private final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";

    @GetMapping("/authors/{fileName}")
    public byte[] getAuthorImage(@PathVariable("fileName") String fileName) throws IOException {
        Path image = Paths.get(UPLOAD_DIRECTORY + "\\authors\\" + fileName);
        if(fileName != null && !fileName.equals("")){
            try {
                return Files.readAllBytes(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY + "\\" + "defaultuser.png"));
    }
    @GetMapping("/books/{bookTitle}/{fileName}")
    public byte[] getBookImage(@PathVariable("fileName") String fileName, @PathVariable("bookTitle") String bookTitle) throws IOException {
        System.out.println("ENTROU");
        Path image = Paths.get(UPLOAD_DIRECTORY + "\\books\\" + bookTitle + "\\" + fileName);
        if(fileName != null && !fileName.equals("")){
            System.out.println("ENTROU NO IF");
            try {
                System.out.println("ENTROU try catch");
                return Files.readAllBytes(image);
            } catch (IOException e) {
                System.out.println("catch");
                e.printStackTrace();
            }
        }
        System.out.println("return detault");
        return Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY + "\\" + "defaultuser.png"));
    }
}
