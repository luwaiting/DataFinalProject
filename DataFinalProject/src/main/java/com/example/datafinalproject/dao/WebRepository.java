package com.example.datafinalproject.dao;

import com.example.datafinalproject.domain.WebSite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebRepository extends JpaRepository<WebSite,Long> {
    Page<WebSite>findAll(Pageable pageable);
}
