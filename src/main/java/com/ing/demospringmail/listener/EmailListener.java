package com.ing.demospringmail.listener;

import com.google.gson.Gson;
import com.ing.demospringmail.model.Customer;
import com.ing.demospringmail.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailListener {
    @Autowired
    private EmailService notificationService;


    @RabbitListener(queues = "DEMO.QUEUE",concurrency = "2")
    public void receive(String in) {
        Gson gson =new Gson();

        Customer customer = gson.fromJson(in, Customer.class);

        try{
            notificationService.sendEmail(customer);

        }
        catch (MailException | IOException | MessagingException mailException){
            System.out.println(mailException);
        }

    }
}
