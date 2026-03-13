package org.rideauthservice.ride_authservice.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rideauthservice.ride_authservice.service.DriverDetailsServiceImpl;
import org.rideauthservice.ride_authservice.service.JwtService;
import org.rideauthservice.ride_authservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DriverDetailsServiceImpl driverDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Incoming request: " + request.getRequestURI());

        String token = null;
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> "JwtToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (token != null) {
            String email = jwtService.extractEmail(token);
            System.out.println("JWT email: " + email);

            boolean authenticated = false;
            if (!authenticated){
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (jwtService.validateToken(token, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        authenticated = true;
                    }
                } catch (Exception ignored) {
                }
            }
            if (!authenticated) {
                try {
                    UserDetails driverDetails = driverDetailsService.loadUserByUsername(email);
                    if (jwtService.validateToken(token, driverDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        driverDetails,
                                        null,
                                        driverDetails.getAuthorities()
                                );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        authenticated = true;
                    }
                } catch (Exception ignored) {
                }
            }

        } else {
            System.out.println("No JWT token found in cookies");
        }

        filterChain.doFilter(request, response);
    }

}