package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>
{
    @Query("FROM Message WHERE postedBy = :postedBy")
    List<Message> getAllMessagesFromUser(@Param("postedBy") Integer postedBy);

    @Query("FROM Message WHERE postedBy = :postedBy AND messageText = :messageText AND timePostedEpoch = :timePostedEpoch")
    Message findMessage(@Param("postedBy") Integer postedBy, @Param("messageText") String messageText, @Param("timePostedEpoch") Long timePostedEpoch);

}