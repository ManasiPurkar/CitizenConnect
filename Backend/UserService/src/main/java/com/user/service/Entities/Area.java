package com.user.service.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "area")
public class Area {
    @Id
    @NotBlank(message="area code cannot be null")
    @Column(name = "areaCode",nullable=false)
    private String areaCode;


    @NotBlank(message = "area name cannot be blank")
    @Column(name = "area_name",nullable=false)
    private String name;
}
