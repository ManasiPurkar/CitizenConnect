package com.user.service.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CommentDTO {


    private String comment;

    private String userName;

    private String userRole;

    private Integer complaintId;

}
