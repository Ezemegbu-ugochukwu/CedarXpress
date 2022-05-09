package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.EmailSenderDto;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    class EmailSenderServiceImplTest {

        @Mock
        EmailSenderServiceImpl emailSenderService;
        @MockBean
        MimeMessage mimeMessage;
        @MockBean
        JavaMailSender mailSender;
        @MockBean
        MimeMessageHelper helper;
        @Test
        void send() {
            EmailSenderDto emailSenderDto = new EmailSenderDto("ccanazodo@gmail.com", "Test Mail", "This is a test Mail");
            Mockito.when(emailSenderService.send(any())).thenReturn(new ResponseEntity<>("Message sent successfully", HttpStatus.OK));
            Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
            ResponseEntity<String> responseEntity = emailSenderService.send(emailSenderDto);
            Assertions.assertThat(responseEntity.getBody()).isEqualTo("Message sent successfully");
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }



