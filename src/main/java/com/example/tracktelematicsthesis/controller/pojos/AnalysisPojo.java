package com.example.tracktelematicsthesis.controller.pojos;

import lombok.Data;

@Data
public class AnalysisPojo {

    private String dateTime;
    private double points;
    private boolean heavyAccel;
    private boolean heavyDecel;
    private boolean zigzagging;
    private int highSpeed;
}
