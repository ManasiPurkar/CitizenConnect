package com.user.service.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Complaints {

    private int complaint_id;
    private int citizenId;
    private String areaCode;
    private String areaName;
    private String title;
    private String description;
    private String address;
    @JsonFormat(pattern = "dd-MMMM-yyyy", timezone = "Asia/Kolkata")
    private Date date;
    private String status;
//    private Integer No_Of_Votes=0;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime eventTime;  // Separate column for time

    private Departments department;
}

