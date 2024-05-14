package com.complaint.service.Entities;

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
    @Pattern(regexp="[a-zA-Z0-9]+", message="Only numbers and characters are allowed")
    @Column(name = "comment",nullable=false)
    private String comment;

    @Column(name = "event_date",nullable=false)
    @Temporal(TemporalType.DATE)
    private Date eventDate;  // Separate column for date

    @Column(name = "event_time",nullable=false)
    @Temporal(TemporalType.TIME)
    private LocalTime eventTime;  // Separate column for time

    @ManyToOne
    @JoinColumn(name = "complaint_id",nullable=false)
    private Complaints complaint;

    //user id
}
