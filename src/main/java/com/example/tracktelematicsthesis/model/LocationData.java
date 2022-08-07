package com.example.tracktelematicsthesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "data")
public class LocationData {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private long UserId;

    private String dateTimeStr;
    private double longitude;
    private double latitude;
    private double speed;

}
