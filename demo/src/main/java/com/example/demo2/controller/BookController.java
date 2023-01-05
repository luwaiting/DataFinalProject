package com.example.demo2.controller;

import com.example.demo2.domain.Book1;
import com.example.demo2.service.BookService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RestController
//@RequestMapping("/v1")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/form")
    public String formula(){
        return "tryFormu";
    }
    @RequestMapping (value = "/uploadImage", method = RequestMethod. POST)
    @ResponseBody
    public String uploadImage2 (@RequestParam ("img") MultipartFile file) throws IOException {
        if (file != null) {
            System.out.println(file);
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getName());
            System.out.println(file.getSize());
            System.out.println(file.getBytes());
            System.out.println(file.getInputStream());
        } else {
            System.out.println("file為空");
        }
        return "tryFormu";
    }
    @RequestMapping (value = "/uploadImage2", method = RequestMethod. POST)
    @ResponseBody
    public String uploadImage (HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile( "img") ;
        if (file != null) {
            System.out.println(file);
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getName());
            System.out.println(file.getSize());
            System.out.println(file.getBytes());
            System.out.println(file.getInputStream());
        } else {
            System.out.println("file為空");
        }
        return "tryFormu";
    }
//    @PostMapping("/form")
//    public String fo(@RequestParam("img")String img){
//
//    }


    @GetMapping("/books")
    public List<Book1> getAll() {

        return bookService.findAll();
    }
    @GetMapping("/booksPage")
    public String getBookPage(Model model) {
       List<Book1>book1= bookService.findAll();
       model.addAttribute("list",book1);
        return "book";
    }
    //first way xdd
    @GetMapping("/tryDividePage")
    public String getDividedPage(
           @PageableDefault(size=5,sort = "id",direction = Sort.Direction.ASC)Pageable pageable,
            Model model) {
        //不需思考如何重製資料庫的id
//        bookService.deleteAll();
//        Book1 book1=new Book1();
//        Book1 book2=new Book1();
//        List<Book1>list=new ArrayList<Book1>();
//        book1.setName("oooooo");
//        book1.setPrice(100);
//        book1.setAuthor("haha");
//        book1.setStatus(1);
//        book2.setName("xd");
//        book2.setPrice(1000);
//        book2.setAuthor("mumu");
//        book2.setStatus(0);
//        list.add(book1);
//        list.add(book2);
//        bookService.saveAll(list);

        Page<Book1>pages=bookService.findAllByPage(pageable);
//       Page<Book1> book1= bookService.findAllByPage();
        model.addAttribute("page",pages);
        return "book";
    }
    //分頁second way傳參
//    @GetMapping("/tryDividePage")
//    public String getDividedPage(
//            @RequestParam(defaultValue = "0")int page,
//            @RequestParam(defaultValue = "5")int size,
//            Model model) {
//        Sort sort=Sort.by(Sort.Direction.DESC,"id");
//        Pageable pageable= PageRequest.of(page,size,sort);
//        Page<Book1>pages=bookService.findAllByPage(pageable);
////       Page<Book1> book1= bookService.findAllByPage();
//        model.addAttribute("page",pages);
//        return "book";
//    }


    //url無注解方式
//    @PostMapping("/books")
//    public Book1 post(Book1 book1){
//        bookService.save(book1);
//       return book1;
//    }
    //json 物件對應屬性方式
    @PostMapping("/books")
    public Book1 post(@RequestBody Book1 book1) {

        bookService.save(book1);
        return book1;
    }

    //一個一個對的方式
//    @PostMapping("/books")
//    public Book1 post(@RequestParam("id")int id,@RequestParam("name")String name
//            ,@RequestParam("author")String aut,@RequestParam("price")int price,@RequestParam("status")int status){
//        Book1 book1=new Book1();
//        book1.setId(id);
//        book1.setName(name);
//        book1.setAuthor(aut);
//        book1.setPrice(price);
//        book1.setStatus(status);
//        bookService.save(book1);
//        return book1;
//    }

    @GetMapping("/books/{id}")
    public Book1 getOneById(@PathVariable("id") long id) {
        Book1 book = bookService.getBookById(id);
        if (book != null) {
            return book;
        } else {
            return null;
        }

    }

    //這種repository原本就定義好的更新必須將那一列資料全部重新輸入才行
    @PutMapping("/books")
    public Book1 updateOne(@RequestBody Book1 book1) {
        bookService.save(book1);
        return book1;
    }
    @PostMapping("books/save")
    public void save(Book1 book1){
//        book1.setId(1);
        bookService.save(book1);
    }
//    @PostMapping("books/saveAll")
//    public void saveAll(){
//        List<Book1>list=new ArrayList<Book1>();
//        Book1 book1=new Book1();
//        Book1 book2=new Book1();
//        book1.setName("yim");
//        book1.setPrice(100);
//        book1.setAuthor("haha");
//        book1.setStatus(1);
//        book2.setName("xd");
//        book2.setPrice(1000);
//        book2.setAuthor("mumu");
//        book2.setStatus(0);
//        list.add(book1);
//        list.add(book2);
//        bookService.saveAll(list);
//    }

    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable("id") long id) {
        bookService.deleteOneById(id);
    }

    @PostMapping("/books/author")
    public List<Book1> findByPrice(@RequestParam("price") int p) {
        return bookService.findBookByPrice(p);
    }

    @PostMapping("/books/authorAndstatus")
    public List<Book1> findByAuthorAndStatus(@RequestParam("author") String au, @RequestParam("status") int sta) {
        return bookService.findByAuthorAndStatus(au, sta);
    }

    @PostMapping("/books/authorcell")
    public List<Book1> findNameByElement(@RequestParam("author") String name) {
        return bookService.findByNameElement(name);
    }

    @PostMapping("/books/fromPrice")
    public List<Book1> findBookByCertainPrice(@RequestParam("price") int p) {
        return bookService.findByCertainPrice(p);
    }

    @PostMapping("/books/updateName")
    public void updateNameById(@RequestParam("name") String name, @RequestParam("id") long id) {
        bookService.updateNameById(name, id);
    }
    @PostMapping("/books/updateNameAndAuthor")
    public void updateNameAndAuthorById(@RequestParam("name") String name,@RequestParam("author")String author) {

        bookService.updateNameAndAuthorById(name,author,17);
    }
    @PostMapping("/books/deleteAll")
    public void deleteAll(){
        bookService.deleteAll();
    }


    @PostMapping("books/deleteByName")
    public void deleteByName(@RequestParam("name") String name) {
        bookService.deleteByName(name);
    }


}
