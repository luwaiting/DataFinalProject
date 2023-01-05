package com.example.datafinalproject.service;

import com.example.datafinalproject.dao.WebRepository;
import com.example.datafinalproject.domain.WebSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebService {
    @Autowired
    private WebRepository webRepository;
    public Page<WebSite>findAllByPage(Pageable pageable){
        return webRepository.findAll(pageable);
    }
    public void deleteAll(){
        webRepository.deleteAll();
    }
    public void save(WebSite webSite){
        webRepository.save(webSite);
    }
    public void saveAll(List<WebSite>webSites){
        webRepository.saveAll(webSites);
    }
    public List<WebSite> findAll(){
        return webRepository.findAll();
    }

}
