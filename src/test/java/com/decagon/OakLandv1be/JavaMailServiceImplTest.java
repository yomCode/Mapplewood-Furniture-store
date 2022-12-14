package com.decagon.OakLandv1be;


import com.decagon.OakLandv1be.services.serviceImpl.JavaMailServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class JavaMailServiceImplTest {
    @Mock
    JavaMailServiceImpl javaMailServiceImpl;

    @Test
    void sendEmail() throws IOException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ilemonamustapha@gmail.com");
        message.setSubject("A test subject");
        message.setText("Hurray! you just received a mail ");
        Mockito.when(javaMailServiceImpl.sendMail(any())).thenReturn(new ResponseEntity<>("sent", HttpStatus.OK));

        ResponseEntity<String> responseEntity = javaMailServiceImpl.sendMail("ilemonamustapha@gmail.com");

                Assertions.assertThat(responseEntity.getBody().equals("sent"));
        Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));



    }

}
