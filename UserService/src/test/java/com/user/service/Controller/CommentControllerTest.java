package com.user.service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.Controllers.CommentController;
import com.user.service.DTOs.CommentDTO;
import com.user.service.Entities.Comments;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerComment_Success() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("This is a test comment")
                .build();

        Comments comment = new Comments();
        comment.setComment("This is a test comment");

        when(commentService.addComment(anyInt(), any(CommentDTO.class))).thenReturn(ResponseEntity.ok(comment));

        mockMvc.perform(post("/add/comment/{userid}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("This is a test comment"));
    }

    @Test
    void registerComment_Failure() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .comment("This is a test comment")
                .build();

        when(commentService.addComment(anyInt(), any(CommentDTO.class))).thenThrow(new APIRequestException("User not found"));

        mockMvc.perform(post("/add/comment/{userid}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}
