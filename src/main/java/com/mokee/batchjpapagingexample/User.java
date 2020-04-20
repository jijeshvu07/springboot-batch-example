package com.mokee.batchjpapagingexample;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "batch_usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String username;
    String password;
    Integer age;
}
