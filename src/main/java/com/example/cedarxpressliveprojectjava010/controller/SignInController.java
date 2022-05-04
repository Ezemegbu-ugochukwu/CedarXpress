package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class SignInController {
    private final LoginService loginService;

    @PostMapping()
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication auth = loginService.login(loginDTO);
        loginService.setUpJWT(auth);
        loginDTO.setPassword(null);
        return ResponseEntity.ok(loginDTO);
    }

    @GetMapping("/log-out")
    public ResponseEntity<String> logout(){
        loginService.logOut();
        return ResponseEntity.ok("Logged-out");
    }
}
