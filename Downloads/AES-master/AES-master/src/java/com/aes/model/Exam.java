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
 * Exam generated by hbm2java
 */
@Entity
@Table(name="exam"
    ,catalog="aes"
)
public class Exam  implements java.io.Serializable {


     private Integer id;
     private Course course;
     private String title;
     private String description;
     private String exam;
     private Date dateCreated;
     private String createdBy;
     private Date dateEdited;
     private String editedBy;
     private Set<ExamScores> examScoreses = new HashSet<ExamScores>(0);
     private int passingPercentage;

    public Exam() {
    }

	
    public Exam(Course course, String title, String description, String exam, Date dateCreated, String createdBy, Date dateEdited, String editedBy) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.exam = exam;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateEdited = dateEdited;
        this.editedBy = editedBy;
    }
    public Exam(Course course, String title, String description, String exam, Date dateCreated, String createdBy, Date dateEdited, String editedBy, Set<ExamScores> examScoreses) {
       this.course = course;
       this.title = title;
       this.description = description;
       this.exam = exam;
       this.dateCreated = dateCreated;
       this.createdBy = createdBy;
       this.dateEdited = dateEdited;
       this.editedBy = editedBy;
       this.examScoreses = examScoreses;
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
    @JoinColumn(name="course", nullable=false)
    @JsonManagedReference
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    @Column(name="title", nullable=false, length=45)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="description", nullable=false)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="exam", nullable=false)
    public String getExam() {
        return this.exam;
    }
    
    public void setExam(String exam) {
        this.exam = exam;
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
    
    @Column(name="passing_percentage", nullable=false)
    public int getPassingPercentage() {
        return this.passingPercentage;
    }
    
    public void setPassingPercentage(int passingPercentage) {
        this.passingPercentage = passingPercentage;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="exam")
    @JsonBackReference
    public Set<ExamScores> getExamScoreses() {
        return this.examScoreses;
    }
    
    public void setExamScoreses(Set<ExamScores> examScoreses) {
        this.examScoreses = examScoreses;
    }        


}


