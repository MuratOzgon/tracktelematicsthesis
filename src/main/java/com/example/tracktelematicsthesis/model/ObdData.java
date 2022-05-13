package com.example.tracktelematicsthesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "obd")
public class ObdData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long UserId;

    private String DeviceTime;
    private double Gx;
    private double Gy;
    private double Gz;
    private double G_calibrated;
    private double AccelerationSensorTotalG;
    private double AccelerationSensorZ_axisG;
    private double Speed;
    private double AccelerationSensorX_axisG;
    private double AccelerationSensorY_axisG;
    private double AverageTripSpeed;

}
