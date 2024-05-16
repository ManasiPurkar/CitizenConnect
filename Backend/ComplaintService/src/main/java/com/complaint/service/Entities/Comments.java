package com.complaint.service.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    @NotBlank(message = "Comment cannot be blank")
    @Pattern(regexp="[a-zA-Z0-9\\s]+", message="Only numbers and characters are allowed")
    @Column(name = "comment",nullable=false)
    private String comment;

    @Column(name = "event_date",nullable=false)
    @Temporal(TemporalType.DATE)
    private Date eventDate;  // Separate column for date

    @Column(name = "event_time",nullable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Temporal(TemporalType.TIME)
    private LocalTime eventTime;  // Separate column for time

    @ManyToOne
    @JoinColumn(name = "complaint_id",nullable=false)
    @JsonBackReference
    private Complaints complaint;

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp="[a-zA-Z\\s]+", message="Only numbers and characters are allowed")
    @Column(name = "userName",nullable=false)
    private String userName;

    @NotBlank(message = "UserRole cannot be blank")
    @Pattern(regexp="(ROLE_ADMIN|ROLE_CITIZEN|ROLE_NAGARSEVAK)", message="Invalid ROLE. Allowed values are: ROLE_ADMIN,ROLE_CITIZEN,ROLE_NAGARSEVAK")
    @Column(name = "userRole",nullable=false)
    private String userRole;

    //user id
}
