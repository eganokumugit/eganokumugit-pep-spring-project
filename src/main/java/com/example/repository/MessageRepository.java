package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>
{
    @Query("FROM message m, account a WHERE m.postedBy = a.accountId AND m.postedBy = :postedBy")
    List<Message> getAllMessagesFromUser(@Param("postedBy") Long postedBy);

    public Message findMessageByMessageTextAndPostedByAndTimePostedEpoch(String messageText, Integer postedBy, Long timePostedEpoch);
}