package com.example.tracktelematicsthesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private long id;

    private double Score;
    private long NumberOfData;

}
