package com.complaint.service.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;

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
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "title",nullable=false)
    private String title;

    @NotBlank(message = "description cannot be blank")
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "description",nullable=false)
    private String description;

    @NotBlank(message = "description cannot be blank")
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "address",nullable=false)
    private String address;

    @NotBlank(message = "Date cannot be blank")
    @Column(name = "date", nullable=false)
    private Date date;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp="(pending|ongoing|solved)", message="Invalid status. Allowed values are: pending, ongoing, solved")
    @Column(name = "status", nullable=false)
    private String status;

    @NotBlank(message = "No_Of_Votes cannot be blank")
    @Column(name = "No_Of_Votes", nullable=false,columnDefinition = "INT DEFAULT 0")
    private Integer No_Of_Votes=0;

    @ManyToOne
    @JoinColumn(name="department_id",nullable=false)
    private Departments department;

    @Column(name = "citizenId", nullable=false)
    private Integer citizenId;

    //area
    //citizen id
    //image

}
