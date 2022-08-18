package com.example.tracktelematicsthesis.controller.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UserDataPojo {

    private String userId;
    List<LocationPojo> location;
    List<AnalysisPojo> analysis;
}
