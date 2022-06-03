package com.example.diningreview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
                          CustomUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String bearerToken = request.getHeader("Authorization");


        if(bearerToken == null || !bearerToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = bearerToken.split(" ")[1];

        if(!jwtTokenUtil.validateToken(token))  {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = jwtTokenUtil.getUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
