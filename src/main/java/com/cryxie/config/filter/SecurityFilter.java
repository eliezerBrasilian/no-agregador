package com.cryxie.config.filter;

import com.cryxie.config.TokenJwt;
import com.cryxie.models.User;
import com.cryxie.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.cryxie.utils.AppUtils.isTestMode;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenJwt tokenServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            var email = tokenServiceImpl.validateToken(token);

            if (isTestMode) {
                var userModel = new User().getUserMocked();
                var userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(userModel.email)
                        .password(userModel.getPassword())
                        .authorities(userModel.getAuthorities())
                        .build();
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                var userModelOptional = userRepository.findByEmail(email);

                if (userModelOptional.isPresent()) {
                    var userModel = userModelOptional.get();
                    var userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(userModel.email)
                            .password(userModel.getPassword())
                            .authorities(userModel.getAuthorities())
                            .build();
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}