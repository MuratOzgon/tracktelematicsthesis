package com.example.tracktelematicsthesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "data")
public class CarData {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private long UserId;

    private String dateTimeStr;
    private double points;
    private boolean heavyAccel;
    private boolean heavyDecel;
    private boolean zigzagging;
}
