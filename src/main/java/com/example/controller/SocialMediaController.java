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
    @Autowired
    public SocialMediaController(AccountService accService, MessageService msgService)
    {
        this.accService = accService;
        this.msgService = msgService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> postRegistrationHandler(@RequestBody Account acc) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
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
        ObjectMapper mapper = new ObjectMapper();
        Account loggedIn = accService.loginUser(acc);
        if(loggedIn == null)
        {
            return ResponseEntity.status(400).body("");
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
        ObjectMapper mapper = new ObjectMapper();
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
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgList = msgService.getAllMessages();
        String jsonResponse = mapper.writeValueAsString(msgList);
        return ResponseEntity.status(200).body(jsonResponse); 
    }
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<String> getMessageWithIdHandler(@RequestBody Long msgId) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = msgService.getMessageById(msgId);
        String jsonResponse = mapper.writeValueAsString(msg);
        return ResponseEntity.status(200).body(jsonResponse); 
    }
    
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageHandler(@RequestBody Long msgId) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Integer delete = msgService.deleteMessage(msgId);
        String jsonResponse = mapper.writeValueAsString(delete);
        return ResponseEntity.status(200).body(jsonResponse); 
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessageHandler(@RequestBody Long msgId, String newMsg) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Integer update = msgService.updateMessage(msgId, newMsg);
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
