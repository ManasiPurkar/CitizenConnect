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

@Service
@AllArgsConstructor
@EnableTransactionManagement
public class CommentServiceImpl implements CommentService {
    private ComplaintsRepository complaintsRepository;
    private CommentsRepository commentsRepository;
    @Override
    public Comments createComment(CommentDTO commentDTO)
    {
        Optional<Complaints>complaint=complaintsRepository.findById(commentDTO.getComplaintId());
        if(complaint.isEmpty())
            throw new APIRequestException("wrong complaint id");
        Comments comment= Comments.builder()
                .comment(commentDTO.getComment())
                .userName(commentDTO.getUserName())
                .userRole(commentDTO.getUserRole())
                .eventTime(LocalTime.now())
                .eventDate(Date.valueOf(LocalDate.now()))
                .complaint(complaint.get())
                .build();
        return commentsRepository.save(comment);

    }
}
