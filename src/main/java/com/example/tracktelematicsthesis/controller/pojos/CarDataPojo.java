package com.example.tracktelematicsthesis.controller.pojos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CarDataPojo {

    private String dateTime;
    private double points;
    private boolean heavyAccel;
    private boolean heavyDecel;
    private boolean zigzagging;
    private int highSpeed;
}
