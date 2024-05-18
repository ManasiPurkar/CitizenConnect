package com.user.service.Services;

import com.user.service.DTOs.CommentDTO;
import com.user.service.DTOs.ComplaintDTO;
import com.user.service.Entities.Comments;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<Comments> addComment(Integer userid,CommentDTO commentDTO);
}
