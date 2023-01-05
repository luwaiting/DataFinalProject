package com.example.demo2.service;

import com.example.demo2.domain.Book1;
import com.example.demo2.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    //分頁查詢
    public Page<Book1>findAllByPage(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
    public List<Book1> findAll() {
        return bookRepository.findAll();
    }

    public void save(Book1 book1) {

        bookRepository.save(book1);
    }

    public Book1 getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteOneById(long id) {
        bookRepository.deleteById(id);
    }

    public List<Book1> findBookByPrice(int pri) {
        return bookRepository.findBookByPrice(pri);
    }

    public List<Book1> findByAuthorAndStatus(String au, int status) {
        return bookRepository.findByAuthorAndStatus(au, status);
    }

    public List<Book1> findByNameElement(String name) {
        return bookRepository.findByAuthorContains(name);
    }

    public List<Book1> findByCertainPrice(int p) {
        return bookRepository.findByJPQL(p);
    }

    public void updateNameById(String name, long id) {
        bookRepository.updateByJPQL(name, id);
    }
    public void updateNameAndAuthorById(String name,String author,long id){
        bookRepository.updateNameAndAuthorByJPQL(name,author,id);
    }
    public void deleteAll(){
        bookRepository.deleteAll();
    }
    public void saveAll(List<Book1>list){
        bookRepository.saveAll(list);
    }
    public void deleteByName(String name) {
        bookRepository.deleteByJPQL(name);
    }
}
