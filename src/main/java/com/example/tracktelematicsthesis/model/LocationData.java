package com.example.tracktelematicsthesis.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class LocationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String dateTimeStr;
    private double longitude;
    private double latitude;
    private double speed;

    public LocationData(Long id) {
        this.id = id;
    }
}
