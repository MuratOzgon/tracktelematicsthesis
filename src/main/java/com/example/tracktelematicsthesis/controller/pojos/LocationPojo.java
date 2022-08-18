package com.example.tracktelematicsthesis.controller.pojos;

import lombok.Data;

@Data
public class LocationPojo {

    private String dateTime;
    private double latitude;
    private double longitude;
    private double speed;
}
