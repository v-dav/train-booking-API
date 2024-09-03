package org.example.trainplanet.auth;

import org.example.trainplanet.user.User;
import org.example.trainplanet.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void authenticateGoogle(OAuth2User oauth2User, HttpServletRequest request) {
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findUserByEmail(email)
                .map(existingUser -> {
                    if ("LOCAL".equals(existingUser.getAuthMethod())) {
                        throw new BadCredentialsException("This email is already associated with a local account");
                    }
                    return existingUser;
                })
                .orElseGet(() -> createNewGoogleUser(oauth2User));

        user.setLastLogin(LocalDateTime.now());
        user = userRepository.save(user);
        authenticateUser(user, request);
    }

    private User createNewGoogleUser(OAuth2User oauth2User) {
        User newUser = new User();
        newUser.setEmail(oauth2User.getAttribute("email"));
        newUser.setFirstname(oauth2User.getAttribute("given_name"));
        newUser.setLastname(oauth2User.getAttribute("family_name"));
        newUser.setProfilePictureUrl(oauth2User.getAttribute("picture"));
        newUser.setGoogleId(oauth2User.getAttribute("sub"));
        newUser.setAuthMethod("GOOGLE");
        newUser.setRole("ROLE_USER");
        newUser.setFirstLogin(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public void authenticateLocal(String email, String password, HttpServletRequest request) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getAuthMethod().equals("GOOGLE")) {
            throw new BadCredentialsException("This account uses Google authentication");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid password");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        authenticateUser(user, request);
    }

    public void registerUser(RegisterRequest registerRequest, HttpServletRequest request) {
        if (userRepository.findUserByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setAuthMethod("LOCAL");
        newUser.setFirstLogin(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setRole("ROLE_USER");
        userRepository.save(newUser);

        authenticateUser(newUser, request);
    }

    // Common method to set the authentication and create a session
    private void authenticateUser(User user, HttpServletRequest request) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}
