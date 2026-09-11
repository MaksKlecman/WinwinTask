package com.example.auth_api.service;


import com.example.auth_api.exception.EmailAlreadyExistsException;
import com.example.auth_api.exception.InvalidCredentialsException;
import com.example.auth_api.model.User;
import com.example.auth_api.repository.UserRepository;
import com.example.auth_api.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(String email, String rawPassword)
    {

        if(userRepository.existsByEmail(email))
        {
            throw  new EmailAlreadyExistsException(email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        userRepository.save(user);


    }

    public String login(String email, String rawPassword)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(rawPassword, user.getPasswordHash()))
        {
            throw new InvalidCredentialsException();
        }

        return jwtUtil.generateToken(email);


    }




}
