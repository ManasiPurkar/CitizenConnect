package com.user.service.Controllers;

import com.user.service.DTOs.CommentDTO;
import com.user.service.DTOs.ComplaintDTO;
import com.user.service.Entities.Area;
import com.user.service.Entities.Comments;
import com.user.service.Entities.Complaints;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.UserRepository;
import com.user.service.Services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/add")
//@CrossOrigin(origins = "*")
public class CommentController {
   @Autowired
   private CommentService commentService;
    @Validated
    @PostMapping("/comment/{userid}")
    public ResponseEntity<Comments> registerComment(@PathVariable Integer userid, @Valid @RequestBody CommentDTO commentDTO)
    {
        return commentService.addComment(userid,commentDTO);
    }
}
