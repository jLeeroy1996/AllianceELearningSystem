package com.aes.model;
// Generated Sep 5, 2015 8:45:49 AM by Hibernate Tools 4.3.1


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
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * UserType generated by hbm2java
 */
@Entity
@Table(name="user_type"
    ,catalog="aes"
    , uniqueConstraints = @UniqueConstraint(columnNames="user_type") 
)
public class UserType  implements java.io.Serializable {


     private Integer id;
     private String userType;
     private Set<User> users = new HashSet<User>(0);

    public UserType() {
    }

	
    public UserType(String userType) {
        this.userType = userType;
    }
    public UserType(String userType, Set<User> users) {
       this.userType = userType;
       this.users = users;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="user_type", unique=true, nullable=false, length=25)
    public String getUserType() {
        return this.userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="userType")
@JsonBackReference
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }




}

