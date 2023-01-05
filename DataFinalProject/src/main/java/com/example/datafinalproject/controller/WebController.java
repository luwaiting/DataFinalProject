package com.example.datafinalproject.controller;

import com.example.datafinalproject.domain.*;
import com.example.datafinalproject.domain.Record;
import com.example.datafinalproject.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
//@RequestMapping("")
public class WebController {
    @Autowired
    WebService webService;
    @Value("${SavePath.ProfilePhoto}")
    private String ProfilePhotoSavePath;
    @Value("${SavePath.ProfilePhotoMapper}")
    private String ProfilePhotoMapperPath;
    @GetMapping("/home")
    public String mainPage(){
        return "main";
    }

//    @ModelAttribute("result")ArrayList<WebSite>list,
    @GetMapping("/nextPage")
    public String nextPage(@ModelAttribute("input")String input,
                            Model model,
                           @PageableDefault(page = 0,size = 5,sort="id",direction = Sort.Direction.ASC)Pageable pageable
                           ){
        Page<WebSite>page=webService.findAllByPage(pageable);
        model.addAttribute("page",page);
        model.addAttribute("keyword",input);
        return "nextPage";
    }
    //get final web list and put into database(update way)
    // then call repository method to get each page
    @PostMapping("/post")
    public String homePage(@RequestParam("keyword")String keyword
                            , RedirectAttributes redirectAttributes){

        System.out.println(keyword);
        Lists<String>record= new Record();
        record.add(keyword);
        Manager manager=new Manager();
        ArrayList<Sorter>result= manager.integrate(keyword);
        if(!webService.findAll().isEmpty()){
            webService.deleteAll();
        }
        ArrayList<WebSite>intoDbs=new ArrayList<WebSite>();
        for(Sorter s:result){
            System.out.println(s.title);
            WebSite webSite=new WebSite();
            webSite.setTitle(s.title);
            webSite.setDescription(s.description);
            webSite.setUrl(s.root.web.url);
            intoDbs.add(webSite);
        }
        webService.saveAll(intoDbs);
//        redirectAttributes.addFlashAttribute("page",page);
//        redirectAttributes.addFlashAttribute("result",intoDbs);
        redirectAttributes.addFlashAttribute("input",keyword);
        //repeat if user change input
        //not sure
        return "redirect:/nextPage";
    }
    @GetMapping("/aboutPage")
    public String aboutPage(){
        return "about";
    }
    @GetMapping("/recordPage")
    public String recordPage(Model model){
        Lists<String>record=new Record();
        model.addAttribute("record",record.getList());
        return "record";
    }

    @PostMapping("/profilePhotoUpload")
    public String profilePhotoUpload(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes){
        String fileName=multipartFile.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        fileName= UUID.randomUUID()+suffixName;
        try {
            multipartFile.transferTo(new File(ProfilePhotoSavePath+fileName));
            String path=ProfilePhotoSavePath+fileName;
            redirectAttributes.addFlashAttribute("path",path);
//            return ProfilePhotoMapperPath+fileName;
            System.out.println("執行成功 "+path);
            Vision vision=new VisionHelp();
            ArrayList<Sorter>visionResult=vision.analyze(path);
            if(!webService.findAll().isEmpty()){
                webService.deleteAll();
            }
            ArrayList<WebSite>intoDbs=new ArrayList<WebSite>();
            for(Sorter s:visionResult){
                System.out.println(s.title);
                WebSite webSite=new WebSite();
                webSite.setTitle(s.title);
                webSite.setDescription(s.description);
                webSite.setUrl(s.root.web.url);
                intoDbs.add(webSite);
            }
            webService.saveAll(intoDbs);
            redirectAttributes.addFlashAttribute("input",vision.key);
            //get api result ex:hotdog then put into search engine and set the result in model
            return "redirect:/nextPage";
        }catch (Exception e){
            System.out.println("執行失敗");
            e.printStackTrace();
            return "";
        }
    }

}
