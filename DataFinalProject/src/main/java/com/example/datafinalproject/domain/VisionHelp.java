package com.example.datafinalproject.domain;

import java.util.ArrayList;

public class VisionHelp extends Vision{
    public ArrayList<Sorter>getVisionResult(String keyword){
        Manager manager=new Manager();
        ArrayList<Sorter>visionResult= manager.integrate(keyword);
        return visionResult;
    }
}
