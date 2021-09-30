package com.ing.demospringmail.service;

import com.ing.demospringmail.model.Customer;
import com.ing.demospringmail.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendEmail(Customer customer) throws MailException, MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(customer.getEmail());
        helper.setSubject("Welcome to our Family");

        byte[] encoded = Files.readAllBytes(Paths.get("src/main/resources/templates/mailText.html"));
        String body = new String(encoded, StandardCharsets.UTF_8);
        helper.setText(body, true);

        helper.addInline("topLogo",
                new File("src/main/resources/images/logo/ING_Logo_BeyazBG_Big.png"));
        ImageUtil.textToImage(customer.getName());

        helper.addInline("customerName",
                new File("src/main/resources/images/customer/" + customer.getName() + ".jpg"));

        javaMailSender.send(message);
    }

}
