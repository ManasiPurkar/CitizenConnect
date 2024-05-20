package Controllers;


import com.complaint.service.DTOs.CommentDTO;
import com.complaint.service.Entities.Comments;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Exceptions.ApiExceptionHandler;
import com.complaint.service.Services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.complaint.service.Controllers.CommentController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.mockito.Mockito.*;


public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegisterComment_Success() throws Exception {
        // Arrange
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("Test comment")
                .userName("John Doe")
                .userRole("ROLE_CITIZEN")
                .complaintId(1)
                .build();

        Comments comment = Comments.builder()
                .comment(commentDTO.getComment())
                .userName(commentDTO.getUserName())
                .userRole(commentDTO.getUserRole())
                .build();

        when(commentService.createComment(any(CommentDTO.class))).thenReturn(comment);

        // Act & Assert
        mockMvc.perform(post("/comment/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Test comment"))
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.userRole").value("ROLE_CITIZEN"));
    }


    @Test
    void registerComment_ThrowsAPIRequestException() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("Test comment")
                .userName("John Doe")
                .userRole("ROLE_CITIZEN")
                .complaintId(1)
                .build();

        when(commentService.createComment(any(CommentDTO.class))).thenThrow(new APIRequestException("Invalid complaint ID", "Details about the error"));

        mockMvc.perform(post("/comment/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\": \"Test comment\", \"userName\": \"John Doe\", \"userRole\": \"ROLE_CITIZEN\", \"complaintId\": 1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid complaint ID")));

        verify(commentService, times(1)).createComment(any(CommentDTO.class));
    }

}
