package com.complaint.service.DTOs;

import com.complaint.service.Entities.Area;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ComplaintDTO {

//    private int complaint_id;

    private String title;

    private String description;

    private String address;

//    @JsonFormat(pattern = "dd-MMMM-yyyy", timezone = "Asia/Kolkata")
//    private Date date;

//    private String status;

//    private Integer No_Of_Votes=0;

    private Integer department_code;

    private Integer citizenId;

    private String areaCode;
    private String areaName;

}
