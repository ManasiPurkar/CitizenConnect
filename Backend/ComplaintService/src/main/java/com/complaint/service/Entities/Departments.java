package com.complaint.service.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int department_id;

    @NotBlank(message = "Department name cannot be blank")
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "name",nullable=false)
    private String department_name;


}
