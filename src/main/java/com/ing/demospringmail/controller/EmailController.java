package com.ing.demospringmail.controller;

import com.ing.demospringmail.service.EmailService;
import com.ing.demospringmail.model.Customer;
import com.ing.demospringmail.model.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class EmailController {
    @Autowired
    private EmailService notificationService;

    @Autowired
    private Customer customer;

    @PostMapping(path = "api/sendemail",consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity mailToCustomer(@RequestBody Customer request)
    {
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        try{
            notificationService.sendEmail(customer);

        }
        catch (MailException | IOException | MessagingException mailException){
            System.out.println(mailException);
        }

        MyResponse response=new MyResponse();
        response.setResult("email gonderildi!");
        return ResponseEntity.ok(response);
    }
}
