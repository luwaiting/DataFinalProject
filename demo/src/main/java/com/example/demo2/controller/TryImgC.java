package com.example.demo2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.UUID;

//@RestController
@Controller
public class TryImgC  {
    @Value("${SavePath.ProfilePhoto}")
    private String ProfilePhotoSavePath;
    @Value("${SavePath.ProfilePhotoMapper}")
    private String ProfilePhotoMapperPath;

    @PostMapping("/profilePhotoUpload")
//    @ResponseBody
    public String profilePhotoUpload(@RequestParam("file")MultipartFile multipartFile, RedirectAttributes redirectAttributes){
        String fileName=multipartFile.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        fileName= UUID.randomUUID()+suffixName;
        try {
            multipartFile.transferTo(new File(ProfilePhotoSavePath+fileName));
            String path=ProfilePhotoSavePath+fileName;
            redirectAttributes.addFlashAttribute("path",path);
//            return ProfilePhotoMapperPath+fileName;
            System.out.println("執行成功 "+path);
            //get api result ex:hotdog then put into search engine and set the result in model
            return "redirect:/ho";
        }catch (Exception e){
            System.out.println("執行失敗");
            e.printStackTrace();
            return "";
        }
    }
    @GetMapping("/ho")
    public String next(@ModelAttribute("path")String path, Model model){
        model.addAttribute("path",path);
        return "getImage";
    }
    @GetMapping("/hom")
    public String next(){
        return "tryImage";
    }


}
