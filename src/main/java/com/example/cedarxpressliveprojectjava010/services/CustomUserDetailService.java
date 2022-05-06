package com.example.cedarxpressliveprojectjava010.services;

import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.Role;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findUserByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("user "+ email + "not found"));

        return  new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), List.of( new SimpleGrantedAuthority(user.getRole())));
    }
}
