package com.complaint.service.Repositories;

import com.complaint.service.Entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
