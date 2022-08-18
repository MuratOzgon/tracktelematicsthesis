package com.example.tracktelematicsthesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "data")
public class CarData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String UserId;

    private String dateTimeStr;
    private double points;
    private boolean heavyAccel;
    private boolean heavyDecel;
    private boolean zigzagging;
    private int highSpeed;
}
