package com.example.cedarxpressliveprojectjava010.configuration.jwt;

import com.example.cedarxpressliveprojectjava010.exception.BadCredentialsException;
import com.example.cedarxpressliveprojectjava010.services.BlacklistService;
import com.example.cedarxpressliveprojectjava010.services.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvide;
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    BlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (blacklistService.isTokenBlackListed(token)){
            throw new BadCredentialsException("Token provided is blacklisted!");
        }


        Jws <Claims> claimsJws = tokenProvide.validateToken(token);
        Claims claims = claimsJws.getBody();
        String email = claims.getSubject();
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthoritiesFromClaims(claims);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email,
                null,
                grantedAuthorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
         if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
             return bearerToken.substring(7);
         }
         return null;
    }

    private Set<GrantedAuthority>  getGrantedAuthoritiesFromClaims(Claims claim){
        List<Map<String, String>> authorities = (List<Map<String, String>>) claim.get("Authorities");
        return authorities.stream()
                .map(authority ->
                        new SimpleGrantedAuthority(authority.get("authority"))).collect(Collectors.toSet());
    }
}
