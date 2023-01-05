package com.example.demo2.controller;

import com.example.demo2.domain.Book1;
import com.example.demo2.domain.Key;
import com.example.demo2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/v2")
public class TryLinkController {
    @Autowired
    private BookService bookService;
    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable("id") long id, Model model){
        Book1 book = bookService.getBookById(id);
        model.addAttribute("book",book);
        return "book2";
    }
    @GetMapping("/first")
    public String tryL(){
        return "tryPost";
    }
    @GetMapping("/jump")
    public String tryJ(){   //@ModelAttribute("text")String text,Model model
//        model.addAttribute("textValue",text);

        return "tryJump";
    }
    @PostMapping("/first")
    public String post(@RequestParam("textValue")String textValue, Model model, RedirectAttributes redirectAttributes){
        Key key=new Key();
//        model.addAttribute("textValue",key.tryPost(textValue));
        redirectAttributes.addFlashAttribute("textValue",key.tryPost(textValue));
        return "redirect:/jump";
//        if(textValue!=""){
//            redirectAttributes.addFlashAttribute("text",key.tryPost(textValue));
//            redirectAttributes.addFlashAttribute("list",key.tryPost2(textValue));
//            return "redirect:/jump";
//        }
//        redirectAttributes.addFlashAttribute("text",key.tryPost(textValue));
//        redirectAttributes.addFlashAttribute("list",key.tryPost2(textValue));
//        return "redirect:/first";
    }




//    try the Response
    @GetMapping("/get1")
    public String tryG1(Model model){
        Key key=new Key();
        model.addAttribute("key",key.tryRes1());
        return "tryGet";
    }
    @GetMapping("/get2")
    public String tryG2(Model model){
        Key key=new Key();
        model.addAttribute("key",key.tryRes2());
        return "tryGet2";
    }


}
