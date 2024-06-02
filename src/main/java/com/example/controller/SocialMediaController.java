package com.example.controller;
import com.example.entity.*;
import com.example.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/*
    app.post("/register", this:: postRegistrationHandler);
    app.post("/login", this:: postLoginRegistrationHandler);
    app.post("/messages", this:: postNewMessageHandler);
    app.get("/messages", this:: getMessageHandler);
    app.get("/messages/{message_id}", this:: getMessageWithIdHandler);
    app.delete("/messages/{message_id}", this:: deleteMessageHandler);
    app.get("/accounts/{account_id}/messages", this:: getMessagesFromAccountHandler);
    app.patch("/messages/{message_id}", this :: updateMessageHandler);*/
}
