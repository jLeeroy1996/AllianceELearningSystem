
package com.aes.dto;

import com.aes.model.Chapter;
import com.aes.model.Course;
import java.util.List;

/**
 *
 * Author: Bryan Agustine C. Cabansay
 * Date Created: Sep 21, 2015 5:57:20 PM
 * 
 */

public class CourseDto {
    
    
    private int id;
    private String category;
    private String trainer;
    private String courseTitle;
    private String description;
    private List<Chapter> chapters;

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getDescription() {
        return description;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
    
    public void convert (Course course) {
        this.setId(course.getId());
        this.setCategory(course.getCourseCategory().getCategory());
        this.setCourseTitle(course.getCourseTitle());
        this.setDescription(course.getDescription());
        this.setTrainer(course.getUser().getName());        
    }

}
