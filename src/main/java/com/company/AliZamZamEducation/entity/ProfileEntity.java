package com.company.AliZamZamEducation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String level;
    private String kurs;
    private String bolim;
    private String phone;
    private String userId;
    private LocalDateTime createdDate;
}
