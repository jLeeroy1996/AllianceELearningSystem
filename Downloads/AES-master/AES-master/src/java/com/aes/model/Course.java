package com.aes.model;
// Generated Sep 5, 2015 8:45:49 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name="course"
    ,catalog="aes"
)
public class Course  implements java.io.Serializable {


     private Integer id;
     private CourseCategory courseCategory;
     private User user;
     private String courseTitle;
     private String description;
     private Date dateCreated;
     private String createdBy;
     private Date dateEdited;
     private String editedBy;
     private Set<Exam> exams = new HashSet<Exam>(0);
     private Set<Chapter> chapters = new HashSet<Chapter>(0);
     private Set<CoursesTaken> coursesTakens = new HashSet<CoursesTaken>(0);

    public Course() {
    }

	
    public Course(CourseCategory courseCategory, User user, String courseTitle, String description, Date dateCreated, String createdBy, Date dateEdited, String editedBy) {
        this.courseCategory = courseCategory;
        this.user = user;
        this.courseTitle = courseTitle;
        this.description = description;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateEdited = dateEdited;
        this.editedBy = editedBy;
    }
    public Course(CourseCategory courseCategory, User user, String courseTitle, String description, Date dateCreated, String createdBy, Date dateEdited, String editedBy, Set<Exam> exams, Set<Chapter> chapters, Set<CoursesTaken> coursesTakens) {
       this.courseCategory = courseCategory;
       this.user = user;
       this.courseTitle = courseTitle;
       this.description = description;
       this.dateCreated = dateCreated;
       this.createdBy = createdBy;
       this.dateEdited = dateEdited;
       this.editedBy = editedBy;
       this.exams = exams;
       this.chapters = chapters;
       this.coursesTakens = coursesTakens;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="course_category_id", nullable=false)
    @JsonManagedReference
    public CourseCategory getCourseCategory() {
        return this.courseCategory;
    }
    
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="trainer", nullable=false)
    @JsonManagedReference
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Column(name="course_title", nullable=false, length=45)
    public String getCourseTitle() {
        return this.courseTitle;
    }
    
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    
    @Column(name="description", nullable=false)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_created", nullable=false, length=19)
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    
    @Column(name="created_by", nullable=false, length=15)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_edited", nullable=false, length=19)
    public Date getDateEdited() {
        return this.dateEdited;
    }
    
    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    
    @Column(name="edited_by", nullable=false, length=15)
    public String getEditedBy() {
        return this.editedBy;
    }
    
    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="course")
@JsonBackReference
    public Set<Exam> getExams() {
        return this.exams;
    }
    
    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="course")
@JsonBackReference
    public Set<Chapter> getChapters() {
        return this.chapters;
    }
    
    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="course")
@JsonBackReference
    public Set<CoursesTaken> getCoursesTakens() {
        return this.coursesTakens;
    }
    
    public void setCoursesTakens(Set<CoursesTaken> coursesTakens) {
        this.coursesTakens = coursesTakens;
    }

}

