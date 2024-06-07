package com.tttmars.ss6project.filter;

import com.tttmars.ss6project.model.Users;
import com.tttmars.ss6project.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DynamicAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Users user = userRepository.findByUsername(username);
            if(user == null){
                logger.info("Username not found: " + username);
                throw new UsernameNotFoundException("could not found user..!!");
            }
            String requestURI = request.getRequestURI();
            boolean hasPermission = user.getAuthorities().stream()
                    .anyMatch(authority -> requestURI.matches(authority.getApiPattern()));

            if (!hasPermission) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}