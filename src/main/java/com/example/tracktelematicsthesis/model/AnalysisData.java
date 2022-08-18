package com.example.tracktelematicsthesis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class AnalysisData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String dateTime;
    private double points;
    private boolean heavyAccel;
    private boolean heavyDecel;
    private boolean zigzagging;
    private int highSpeed;

    public AnalysisData(Long id) {
        this.id = id;
    }

    public AnalysisData(User user) {
        this.user = user;
    }
}
