package com.example.controller;
import com.example.entity.*;
import com.example.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController 
{
    AccountService accService;
    MessageService msgService;
    ObjectMapper mapper;
    @Autowired
    public SocialMediaController(AccountService accService, MessageService msgService,ObjectMapper mapper)
    {
        this.accService = accService;
        this.msgService = msgService;
        this.mapper = mapper;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> postRegistrationHandler(@RequestBody Account acc) throws JsonProcessingException
    {
        HashMap<Integer,Account> registrationMap = accService.registerAccount(acc);
        if(registrationMap == null)
        {
            return ResponseEntity.status(400).body("");
        }
        else
        {
            int statusCode = registrationMap.containsKey(200) ? 200 : 409;
            String jsonResponse = mapper.writeValueAsString(registrationMap.get(statusCode));
            return ResponseEntity.status(statusCode).body(jsonResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> postLoginRegistrationHandler(@RequestBody Account acc) throws JsonProcessingException
    {
        Account loggedIn = accService.loginUser(acc);
        if(loggedIn == null)
        {
            return ResponseEntity.status(401).body("");
        }
        else
        {
            String jsonResponse = mapper.writeValueAsString(loggedIn);
            return ResponseEntity.status(200).body(jsonResponse);
        }
    }
    @PostMapping("/messages")
    public ResponseEntity<String> postNewMessageHandler(@RequestBody Message msg) throws JsonProcessingException
    {
        Message newMessage = msgService.addMessage(msg);
        if(newMessage == null)
        {
            return ResponseEntity.status(400).body("");
        }
        else
        {
            String jsonResponse = mapper.writeValueAsString(newMessage);
            return ResponseEntity.status(200).body(jsonResponse);
        }
    }


    @GetMapping("/messages")
    public ResponseEntity<String> getMessageHandler() throws JsonProcessingException
    {
        List<Message> msgList = msgService.getAllMessages();
        String jsonResponse = mapper.writeValueAsString(msgList);
        return ResponseEntity.status(200).body(jsonResponse); 
    }
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<String> getMessageWithIdHandler(@PathVariable("message_id") Integer msgId) throws JsonProcessingException
    {
        Message msg = msgService.getMessageById(msgId);
        String jsonResponse = msg != null ? mapper.writeValueAsString(msg) : "";
        return ResponseEntity.status(200).body(jsonResponse); 
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<String> getMessageWithAccountIdHandler(@PathVariable("account_id") Integer accId) throws JsonProcessingException
    {
        List<Message> msgList = msgService.getAllMessagesFromUser(accId);
        String jsonResponse = msgList != null ? mapper.writeValueAsString(msgList) : "";
        return ResponseEntity.status(200).body(jsonResponse); 
    }
    
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageHandler(@PathVariable("message_id") Integer msgId) throws JsonProcessingException
    {
        if(msgService.getMessageById(msgId) != null)
        {       
            Integer delete = msgService.deleteMessage(msgId);
            String jsonResponse = mapper.writeValueAsString(delete);
            return ResponseEntity.status(200).body(jsonResponse); 
        }
        else
        {
            return ResponseEntity.status(200).body("");
        }
 
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessageHandler(@PathVariable("message_id") Integer msgId,@RequestBody Message newMsg) throws JsonProcessingException
    {
        Integer update = msgService.updateMessage(msgId, newMsg.getMessageText());
        if(update == null)
        {
            return ResponseEntity.status(400).body("");
        }
        else
        {
            String jsonResponse = mapper.writeValueAsString(update);
            return ResponseEntity.status(200).body(jsonResponse);
        }
    }
}
