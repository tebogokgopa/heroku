package com.heroku.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String idno;
    private String name;
    private String surname;




   public long getId() {
        return id;
    }	
   public void setId(long id) {
	   this.id = id;
   }
   
   public void setName(String name) {
	   this.name = name;
   }
   public String getName() {
        return name;
    }
   
   
 public void setSurname(String surname) {
      this.surname = surname;
 }
    
 public String getSurname() {
        return surname;
    }   
    

    public void setIdno(String idno) {
	   this.idno = idno;
   }

    public String getIdno() {
        return idno;
    }
    

 }
    
    
