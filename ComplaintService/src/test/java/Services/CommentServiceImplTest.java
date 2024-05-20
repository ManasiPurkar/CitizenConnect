package Services;


import com.complaint.service.DTOs.CommentDTO;
import com.complaint.service.Entities.Comments;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Repositories.CommentsRepository;
import com.complaint.service.Repositories.ComplaintsRepository;
import com.complaint.service.ServiceImpls.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private ComplaintsRepository complaintsRepository;

    @Mock
    private CommentsRepository commentsRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateComment_Success() {
        // Arrange
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("Test comment")
                .userName("John Doe")
                .userRole("ROLE_CITIZEN")
                .complaintId(1)
                .build();
        Complaints complaint = new Complaints();
        complaint.setComplaint_id(1);

        Comments comment = Comments.builder()
                .comment(commentDTO.getComment())
                .userName(commentDTO.getUserName())
                .userRole(commentDTO.getUserRole())
                .eventTime(LocalTime.now())
                .eventDate(Date.valueOf(LocalDate.now()))
                .complaint(complaint)
                .build();

        when(complaintsRepository.findById(1)).thenReturn(Optional.of(complaint));
        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        // Act
        Comments createdComment = commentService.createComment(commentDTO);

        // Assert
        assertNotNull(createdComment);
        assertEquals(commentDTO.getComment(), createdComment.getComment());
        assertEquals(commentDTO.getUserName(), createdComment.getUserName());
        assertEquals(commentDTO.getUserRole(), createdComment.getUserRole());
        assertEquals(complaint, createdComment.getComplaint());

        verify(complaintsRepository, times(1)).findById(1);
        verify(commentsRepository, times(1)).save(any(Comments.class));
        System.out.println("successful comment tested");
    }

    @Test
    public void testCreateComment_ThrowsAPIRequestException() {
        // Arrange
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("Test comment")
                .userName("John Doe")
                .userRole("ROLE_CITIZEN")
                .complaintId(1)
                .build();

        when(complaintsRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        APIRequestException exception = assertThrows(APIRequestException.class, () -> {
            commentService.createComment(commentDTO);
        });

        assertEquals("wrong complaint id", exception.getMessage());

        verify(complaintsRepository, times(1)).findById(1);
        verify(commentsRepository, never()).save(any(Comments.class));
        System.out.println("wrong comment tested");
    }
}
