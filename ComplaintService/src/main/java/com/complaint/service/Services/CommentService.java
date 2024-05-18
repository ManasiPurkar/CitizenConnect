package com.complaint.service.Services;

import com.complaint.service.DTOs.CommentDTO;
import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Comments;
import com.complaint.service.Entities.Complaints;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

@Service
public interface CommentService {
    //create
    Comments createComment(CommentDTO commentDTO);
}
