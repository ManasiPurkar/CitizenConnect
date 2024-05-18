package com.complaint.service.Controllers;

import com.complaint.service.DTOs.CommentDTO;
import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Comments;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Validated
    @PostMapping("/")
    public ResponseEntity<Comments> registerComment(@Valid @RequestBody CommentDTO commentDTO) {
        try {

            Comments regcomment=commentService.createComment(commentDTO);

            return ResponseEntity.ok(regcomment);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = ex.getCause().getMessage();
            String duplicateEntryMessage = null;

            if (errorMessage.contains("Duplicate entry")) {
                // Extract the part of the message that contains the duplicate entry information
                duplicateEntryMessage = errorMessage.substring(errorMessage.indexOf("Duplicate entry"), errorMessage.indexOf("for key"));
            }

            if (duplicateEntryMessage != null) {
                throw new APIRequestException(duplicateEntryMessage, ex.getMessage());
            } else {
                // If the message doesn't contain the expected format, throw a generic exception
                throw new APIRequestException("Duplicate entry constraint violation occurred", ex.getMessage());
            }
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while registering comment.", ex.getMessage());
        }
    }
}
