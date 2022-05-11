package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.EditUserDetailsDto;
import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<RegistrationDto> registerUser(RegistrationDto registrationDto) {

        if(!Objects.equals(registrationDto.getPassword(), registrationDto.getConfirmPassword())){
            throw new RuntimeException("Passwords do not match");
        }
        if(userRepository.existsByEmail(registrationDto.getEmail())){
           throw new RuntimeException("User already exist");
        }

            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
            registrationDto.setPassword(encodedPassword);
            User user = modelMapper.map(registrationDto,User.class);
            user = userRepository.save(user);


            registrationDto = modelMapper.map(user,RegistrationDto.class);
            registrationDto.setPassword(null);

            return new ResponseEntity<>(registrationDto,HttpStatus.CREATED);
    }

    @Override
    public void editUserDetails(EditUserDetailsDto editUserDetailsDto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.getUserByEmail(loggedInEmail);
        user.setFirstName(editUserDetailsDto.getFirstName());
        user.setLastName(editUserDetailsDto.getLastName());
        user.setDob(editUserDetailsDto.getDob());
        user.setGender(editUserDetailsDto.getGender());

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user "+ email + "not found"));

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
