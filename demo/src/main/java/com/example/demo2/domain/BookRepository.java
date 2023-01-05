package com.example.demo2.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

//@Repository
public interface BookRepository extends JpaRepository<Book1, Long> {
    List<Book1> findBookByPrice(int pri);
    Page<Book1>findAll(Pageable pageable);

    List<Book1> findByAuthorAndStatus(String au, int s);

    List<Book1> findByAuthorContains(String name);

    @Query("select b from Book1 b where b.price > ?1")
    List<Book1> findByJPQL(int price);

    @Transactional
    @Modifying
    @Query("update Book1 b set b.name=?1 where b.id = ?2")
    void updateByJPQL(String name, long id);

    @Transactional
    @Modifying
    @Query("update Book1 b set b.name=?1 ,b.author=?2 where b.id = ?3")
    void updateNameAndAuthorByJPQL(String name,String author, long id);


    @Transactional
    @Modifying
    @Query("delete from Book1 b where b.name=?1")
    void deleteByJPQL(String name);
}
