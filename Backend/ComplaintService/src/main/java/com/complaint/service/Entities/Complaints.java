package com.complaint.service.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaints")
public class Complaints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int complaint_id;

    @NotBlank(message = "Title cannot be blank")
    @Pattern(regexp="[a-zA-Z\\s]+", message="Only characters and spaces are allowed")
    @Column(name = "title",nullable=false)
    private String title;

    @NotBlank(message = "description cannot be blank")
    @Pattern(regexp="[a-zA-Z\\s]+", message="Only characters and spaces are allowed")
    @Column(name = "description",nullable=false)
    private String description;

    @NotBlank(message = "address cannot be blank")
    @Pattern(regexp="[a-zA-Z\\s]+", message="Only characters and spaces are allowed")
    @Column(name = "address",nullable=false)
    private String address;

    @JsonFormat(pattern = "dd-MMMM-yyyy", timezone = "Asia/Kolkata")
//    @NotBlank(message = "Date cannot be blank")
    @Column(name = "date", nullable=false)
    private Date date;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp="(pending|ongoing|solved)", message="Invalid status. Allowed values are: pending, ongoing, solved")
    @Column(name = "status", nullable=false)
    private String status;

//    @NotBlank(message = "No_Of_Votes cannot be blank")
//    @Column(name = "No_Of_Votes", nullable=false,columnDefinition = "INT DEFAULT 0")
//    private Integer No_Of_Votes=0;
    @Column(name = "event_time",nullable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime eventTime;  // Separate column for time

    @ManyToOne
    @JoinColumn(name="department_id",nullable=false)
    private Departments department;

    @Column(name = "citizenId", nullable=false)
    private Integer citizenId;

    @Column(name = "areaCode", nullable=false)
    private String areaCode;

    //area
    //image

}
