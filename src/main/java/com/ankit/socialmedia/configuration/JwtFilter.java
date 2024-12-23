package com.ankit.socialmedia.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConst.JWT_HEADER);

        if(jwt != null && jwt.startsWith("Bearer ")){
            try{
                String email = JwtProvider.extractEmailByToken(jwt);
                List<GrantedAuthority> authorities = new ArrayList<>();
                Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                e.printStackTrace();
                throw new BadCredentialsException("Invalid Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
