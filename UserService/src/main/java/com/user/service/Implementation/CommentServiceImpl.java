package com.user.service.Implementation;

import com.user.service.DTOs.CommentDTO;
import com.user.service.DTOs.ComplaintDTO;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Comments;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Repositories.NagarsevakRepository;
import com.user.service.Services.CitizenService;
import com.user.service.Services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private NagarsevakRepository nagarsevakRepository;
    @Override
    public ResponseEntity<Comments> addComment(Integer userid, CommentDTO commentDTO)
    {
        String userRole= commentDTO.getUserRole();
        String userName=null;
        if(Objects.equals(userRole, "ROLE_CITIZEN"))
        {
            Optional<Citizen> citizen=citizenRepository.findById(userid);
            if(citizen.isEmpty())
                throw new APIRequestException("Incorrect User Id of Citizen");
            userName=citizen.get().getFirstname()+" "+citizen.get().getLastname();

        } else if (Objects.equals(userRole, "ROLE_NAGARSEVAK")) {
            Optional<Nagarsevak> nagarsevak=nagarsevakRepository.findById(userid);
            if(nagarsevak.isEmpty())
                throw new APIRequestException("Incorrect user id of Nagarsevak");
            userName=nagarsevak.get().getFirstname()+" "+nagarsevak.get().getLastname();
        }
        else
        {
            throw new APIRequestException("Wrong Role !");
        }
        commentDTO.setUserName(userName);
        return complaintService.registerComment(commentDTO);
    }
}
