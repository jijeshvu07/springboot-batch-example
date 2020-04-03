package com.mokee.batchjpapagingexample;

import javax.persistence.*;

import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User u")
})
@Table(name = "batch_user")
@Data
@Entity
public class User {
 
  @Id
  @GeneratedValue
  int id;
  String username;
  String password;
  int age;
}