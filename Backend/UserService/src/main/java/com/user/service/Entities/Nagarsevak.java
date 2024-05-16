package com.user.service.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nagarsevak")
public class Nagarsevak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nagarsevak_id;

    @NotBlank(message = "first_name cannot be blank")
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "first_name",nullable=false)
    private String firstname;

    @NotBlank(message = "last_name cannot be blank")
    @Pattern(regexp="[a-zA-Z]+", message="Only characters are allowed")
    @Column(name = "last_name",nullable=false)
    private String lastname;

    @NotBlank(message = "mobile no cannot be blank")
    @Pattern(regexp="^\\d{10}$", message="Only 10 numbers are allowed")
    @Column(name = "mobile_no", unique = true,nullable=false)
    private String mobile_no;

//    @Email(message = "Enter valid email")
//    @Column(name = "email", unique = true,nullable=false)
//    private String email;

    @ManyToOne
    @JoinColumn(name = "areaCode",nullable=false)
    private Area area;

    @OneToOne
    @JoinColumn(name="email",nullable=false)
    private Users user;

    @Column(columnDefinition = "boolean default true")
    private Boolean active=true;
}
