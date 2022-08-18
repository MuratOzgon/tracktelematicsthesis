package com.example.tracktelematicsthesis.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    @OneToMany(mappedBy = "user")
    private List<LocationData> location;

    @OneToMany(mappedBy = "user")
    private List<AnalysisData> analysis;

    public User(String userId) {
        this.userId = userId;
    }
}
