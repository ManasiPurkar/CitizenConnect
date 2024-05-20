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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private NagarsevakRepository nagarsevakRepository;
    @Override
    public ResponseEntity<Comments> addComment(Integer userid, CommentDTO commentDTO)
    {
        logger.info("Adding comment for user ID: {} with role: {}", userid, commentDTO.getUserRole());

        String userRole= commentDTO.getUserRole();
        String userName=null;
        if(Objects.equals(userRole, "ROLE_CITIZEN"))
        {
            Optional<Citizen> citizen=citizenRepository.findById(userid);
            if(citizen.isEmpty()) {
                logger.error("Incorrect User ID of Citizen: {}", userid);
                throw new APIRequestException("Incorrect User Id of Citizen");
            }
            userName=citizen.get().getFirstname()+" "+citizen.get().getLastname();
            logger.debug("Citizen found with ID: {} and name: {}", userid, userName);


        } else if (Objects.equals(userRole, "ROLE_NAGARSEVAK")) {
            Optional<Nagarsevak> nagarsevak=nagarsevakRepository.findById(userid);
            if(nagarsevak.isEmpty()) {
                logger.error("Incorrect User ID of Nagarsevak: {}", userid);

                throw new APIRequestException("Incorrect user id of Nagarsevak");
            }
            userName=nagarsevak.get().getFirstname()+" "+nagarsevak.get().getLastname();
            logger.debug("Nagarsevak found with ID: {} and name: {}", userid, userName);

        }
        else
        {
            logger.error("Wrong role provided: {}", userRole);

            throw new APIRequestException("Wrong Role !");
        }
        commentDTO.setUserName(userName);
        logger.info("Registering comment for user: {}", userName);

        return complaintService.registerComment(commentDTO);
    }
}
