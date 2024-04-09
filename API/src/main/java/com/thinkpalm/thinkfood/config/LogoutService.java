package com.thinkpalm.thinkfood.config;

import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Causes a logout to be completed. The method must complete successfully.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        userEmail=jwtService.extractUsername(jwt);
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        userOptional.ifPresent(user -> {
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails) && jwt.equals(user.getAuthenticationToken())) {
                log.info("tokens are same");
                user.setAuthenticationToken(null);



        // Clear the security context
        SecurityContextHolder.clearContext();
    }

    });

    }
}