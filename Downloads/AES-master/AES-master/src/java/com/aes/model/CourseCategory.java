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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * CourseCategory generated by hbm2java
 */
@Entity
@Table(name="course_category"
    ,catalog="aes"
    , uniqueConstraints = @UniqueConstraint(columnNames="category") 
)
public class CourseCategory  implements java.io.Serializable {


     private Integer id;
     private String category;
     private Date dateCreated;
     private String createdBy;
     private Date dateEdited;
     private String editedBy;
     private Set<Course> courses = new HashSet<Course>(0);

    public CourseCategory() {
    }

	
    public CourseCategory(String category, Date dateCreated, String createdBy, Date dateEdited, String editedBy) {
        this.category = category;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateEdited = dateEdited;
        this.editedBy = editedBy;
    }
    public CourseCategory(String category, Date dateCreated, String createdBy, Date dateEdited, String editedBy, Set<Course> courses) {
       this.category = category;
       this.dateCreated = dateCreated;
       this.createdBy = createdBy;
       this.dateEdited = dateEdited;
       this.editedBy = editedBy;
       this.courses = courses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="category", unique=true, nullable=false, length=50)
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="courseCategory")
    @JsonBackReference
    public Set<Course> getCourses() {
        return this.courses;
    }
    
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }




}

