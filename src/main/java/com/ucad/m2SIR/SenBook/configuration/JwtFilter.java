package com.ucad.m2SIR.SenBook.configuration;

import com.ucad.m2SIR.SenBook.repository.TokenRepository;
import com.ucad.m2SIR.SenBook.service.UserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserAuthService userAuthService;
    private final TokenRepository tokenRepository;

    public JwtFilter(JwtService jwtService, UserAuthService userAuthService, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(header != null && header.startsWith("Bearer "))
        {
            token = header.substring(7);
            username = jwtService.extractUsername(token);
        }
        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = userAuthService.loadUserByUsername(username);
            boolean isTokenValid = jwtService.validateToken(token,userDetails);
            boolean isTokenValidFromDB = tokenRepository.findByToken(token)
                    .map(t-> !t.getExpired() && !t.getRevoked())
                    .orElse(false);
            if(isTokenValid && isTokenValidFromDB)
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
