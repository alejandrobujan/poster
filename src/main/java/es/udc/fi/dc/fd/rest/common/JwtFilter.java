package es.udc.fi.dc.fd.rest.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Class JwtFilter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    
    /** The jwt generator. */
    @Autowired
    private JwtGenerator jwtGenerator;

    /**
     * Do filter internal.
     *
     * @param request the request
     * @param response the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (authHeaderValue == null || !authHeaderValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            
            String serviceToken = authHeaderValue.replace("Bearer ", "");
            JwtInfo jwtInfo = jwtGenerator.getInfo(serviceToken);
            
            request.setAttribute("serviceToken", serviceToken);
            request.setAttribute("userId", jwtInfo.getUserId());
            
            configureSecurityContext(jwtInfo.getUserName(), jwtInfo.getRole());
            
        } catch (Exception e) {
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             return;
        }
        
        filterChain.doFilter(request, response);
        
    }
    
    /**
     * Configure security context.
     *
     * @param userName the user name
     * @param role the role
     */
    private void configureSecurityContext(String userName, String role) {
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userName, null, authorities));
        
    }

}
