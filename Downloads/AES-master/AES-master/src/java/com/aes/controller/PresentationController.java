
package com.aes.controller;

import com.aes.model.Presentation;
import com.aes.service.ChapterService;
import com.aes.service.PresentationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 21, 2015 2:05:35 PM
 * 
 */

@Controller
public class PresentationController {

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private PresentationService presentationService;
    
    @Autowired
    private ChapterService chapterService;
    
    @RequestMapping(value="post/presentation", method=RequestMethod.POST)
    @ResponseBody
    private JSONObject createPresentation(@RequestBody JSONObject data) {
        JSONObject json = new JSONObject();
        Presentation presentation = new Presentation ();
        
        try {
            presentation.setChapter(chapterService.get(Integer.parseInt((String)data.get("id"))));
            presentation.setLocation((String)data.get("path"));
            presentation.setCreatedBy((String)data.get("admin"));
            presentation.setName((String)data.get("name"));
            presentationService.add(presentation);
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="get/chapter/presentations/{chapterId}", method=RequestMethod.GET)
    @ResponseBody
    private JSONObject getPresentations (@PathVariable(value="chapterId") int id) {
        
        JSONObject json = new JSONObject ();
        ObjectMapper mapper = new ObjectMapper();        
        try {
            json.put("presentations", mapper.writeValueAsString(presentationService.getByChapterId(id)));
            json.put("error", "");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;        
    }
    
    @RequestMapping(value="/download", method=RequestMethod.GET)
    private void downloadPresentation (HttpServletResponse res, HttpServletRequest req) { 
        try {
                String path = req.getParameter("file");
                String fileName = req.getParameter("name");
                File f = new File(path);
                if (f.exists()) {
                    res.setContentLength(new Long(f.length()).intValue());
                    res.setHeader("Content-Disposition", "attachment; " + fileName);
                    res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                    OutputStream out = res.getOutputStream();
                    res.setContentType("text/plain; charset=utf-8");
                    FileInputStream fi = new FileInputStream(f);
                    IOUtils.copy(fi, out);
                    out.flush();
                    out.close();                     
                }
            } catch (Exception e) {
                e.printStackTrace();
            }      
    }
    
    @RequestMapping(value="delete/presentation", method=RequestMethod.POST)
    private @ResponseBody JSONObject deletePresentation (@RequestBody JSONObject data) {
        JSONObject json = new JSONObject();
        try{
            Presentation presentation = presentationService.get(Integer.parseInt((String)data.get("id")));
            File file = new File(presentation.getLocation()); 
            file.delete();
            presentationService.delete(presentation);
            json.put("error", "");
        } catch (Exception e){
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }        
        return json;
    }
    
    @RequestMapping(value="upload/file", method=RequestMethod.POST )
    private @ResponseBody JSONObject uploadFile(@RequestParam("file") MultipartFile file){
        long maxSize = (1024 * 1024 * 50);
        JSONObject json = new JSONObject();
        List<String> errorList = new ArrayList<String> ();
        String fileName = file.getOriginalFilename();
        String path = servletContext.getRealPath("") + File.separator + "uploads" + File.separator + fileName;
        try {
            if (file.getSize() > maxSize){
                errorList.add("File is too big. Limit it to 50Mb.");
                System.out.println("File is too big. Limit it to 50Mb.");
            }            
            if (file == null || file.getSize() == 0) {
                errorList.add("Please upload a file.");
            }
            if (presentationService.isExisting(fileName)){
                errorList.add("File alreadys exists. Please choose another file name.");
            }
            if (errorList.isEmpty()){                
                File destFile = new File(path);
                file.transferTo(destFile);
                json.put("path", path);
                json.put("name", fileName);
                json.put("error", "");
            } else {
                json.put("error", errorList.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Server Error: " + e.getMessage());
        }      
       return json;
    }
        
}
