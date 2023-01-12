package com.example.datafinalproject.domain;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class Vision {
    public String key;
    public ArrayList<Sorter> analyze(String imgPath) throws Exception{
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // The path to the image file to annotate
            String fileName = imgPath;
            Map<String,Double>com=new HashMap<String,Double>();
            // Reads the image file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            String result="";
            String compare="";
            String keyCom="";
            int c=0;
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                }
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {

                    Set<FieldDescriptor> set=annotation.getAllFields().keySet();
                    Iterator<FieldDescriptor> it = set.iterator();

                    while(it.hasNext()) {
                        FieldDescriptor key=it.next();
                        c++;
                        result=annotation.getAllFields().get(key).toString();
                        compare=result;
                        if(c==2) {
                            keyCom=compare;
                        }
                        if(c==4) {
                            double value=Double.parseDouble(compare);
                            com.put(keyCom,value);
                            c=0;
                            compare="";
                        }
                    }
                }
            }
            Set<String>set=com.keySet();
            Iterator<String>it=set.iterator();
            double max=0;
            String f="";
            while(it.hasNext()) {
                String key=it.next();
                if(key.contains("Head")||key.contains("Arm") ||key.contains("Mouth")||key.contains("body")||key.contains("Eyelash")
                        ||key.contains("Hair")||key.contains("Lip")||key.contains("Food")){
                    continue;
                }
                double score=com.get(key);
                if(key.contains(" ")) {
                    score+=0.05;
                }
                if(score>max) {
                    f=key;
                    max=score;
                }else {
                    continue;
                }
//	    	System.out.print(key+" "+com.get(key));
            }
//            if(f.equals("Hot dog")){
//                key="熱狗";
//                return getVisionResult("熱狗");
//            }
            if(f.contains("braces")){
                key="braces163";
                return getVisionResult("braces163");
            }
            if(f.contains(" ")){
                key=f.replace(" ","");
                return getVisionResult(key);
            }else{
                return getVisionResult(f);
            }
        }
    }
    public abstract ArrayList<Sorter>getVisionResult(String keyword);

}
