package com.complaint.service.ServiceImpls;

import com.complaint.service.DTOs.CommentDTO;
import com.complaint.service.Entities.Comments;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Repositories.CommentsRepository;
import com.complaint.service.Repositories.ComplaintsRepository;
import com.complaint.service.Services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.xml.stream.events.Comment;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@EnableTransactionManagement
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private ComplaintsRepository complaintsRepository;
    private CommentsRepository commentsRepository;
    //creating comment
    @Override
    public Comments createComment(CommentDTO commentDTO)
    {
        logger.info("Creating comment...");
        Optional<Complaints>complaint=complaintsRepository.findById(commentDTO.getComplaintId());
        if (complaint.isEmpty()) {
            logger.error("Wrong complaint id: {}", commentDTO.getComplaintId());
            throw new APIRequestException("wrong complaint id");
        }
        
        Comments comment= Comments.builder()
                .comment(commentDTO.getComment())
                .userName(commentDTO.getUserName())
                .userRole(commentDTO.getUserRole())
                .eventTime(LocalTime.now())
                .eventDate(Date.valueOf(LocalDate.now()))
                .complaint(complaint.get())
                .build();
        Comments savedComment = commentsRepository.save(comment);
        logger.info("Comment created successfully with id: {}", savedComment.getComment_id());
        return savedComment;

    }
}
