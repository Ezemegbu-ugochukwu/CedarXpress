package com.example.cedarxpressliveprojectjava010;

import com.example.cedarxpressliveprojectjava010.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CedarXpressLiveprojectJava010Application {

    public static void main(String[] args) {
        SpringApplication.run(CedarXpressLiveprojectJava010Application.class, args);

    }
}
