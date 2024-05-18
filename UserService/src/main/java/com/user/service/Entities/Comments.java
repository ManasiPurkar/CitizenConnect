package com.user.service.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;
import java.time.LocalTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    private int comment_id;

    private String comment;

    private Date eventDate;  // Separate column for date

    private LocalTime eventTime;  // Separate column for time

    private String userName;

    private String userRole;

    //user id
}
