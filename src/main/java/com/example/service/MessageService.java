package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService 
{
    AccountRepository accRepo;
    MessageRepository msgRepo;

    @Autowired
    public MessageService(AccountRepository accRepo, MessageRepository msgRepo)
    {
        this.accRepo = accRepo;
        this.msgRepo = msgRepo;
    }

    public Message addMessage(Message msg)
    {
        if(msg.getMessageText().length() <= 255 && msg.getMessageText().length() > 0 && accRepo.findAccountByAccountId(msg.getPostedBy()) != null)
        {
            msgRepo.save(msg);
            Message addedMessage = msgRepo.findMessage(msg.getPostedBy(),msg.getMessageText(), msg.getTimePostedEpoch());
            return addedMessage;
        }
        else
        {
            return null;
        }
        
    }

    public List<Message> getAllMessages()
    {
        return msgRepo.findAll();
    }

    public Message getMessageById(Long msgId)
    {
        Optional<Message> optionalMsg = msgRepo.findById(msgId);
        if(optionalMsg.isPresent()){
            return optionalMsg.get();
        }else{
            return null;
        }
    }

    public Integer deleteMessage(Long msgId)
    {
        if(msgRepo.findById(msgId) == null)
        {
            return null;
        }
        else
        {
            msgRepo.deleteById(msgId);
            return 1;
        }
    }

    public Integer updateMessage(Long msgId, String newMsg)
    {
        Optional<Message> optionalMessage = msgRepo.findById(msgId);
        if(optionalMessage.isPresent() && newMsg.length() <= 255 && newMsg.length() > 0)
        {
            Message updatedMsg = optionalMessage.get();
            updatedMsg.setMessageText(newMsg);
            msgRepo.save(updatedMsg);
            return 1;
        }
        else
        {
            return null;
        }
    }
        


    public List<Message> getAllMessagesFromUser(Integer id)
    {
        return msgRepo.getAllMessagesFromUser(id);
    }
}
