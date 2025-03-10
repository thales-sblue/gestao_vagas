package br.com.thalesdev.gestao_vagas.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.thalesdev.gestao_vagas.providers.JWTCandidateProvider;
import br.com.thalesdev.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Libera rotas do Swagger
        if (request.getRequestURI().startsWith("/swagger-ui") ||
                request.getRequestURI().startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Libera rotas públicas
        if (isPublicRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {

            if (request.getRequestURI().startsWith("/company")) {
                var token = this.jwtProvider.validateToken(header);
                request.setAttribute("company_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } else if (request.getRequestURI().startsWith("/candidate")) {
                var token = this.jwtCandidateProvider.validateToken(header);
                request.setAttribute("candidate_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicRoute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return (method.equals("POST") && (uri.equals("/candidate/auth") ||
                uri.equals("/candidate/") ||
                uri.equals("/company/auth") ||
                uri.equals("/company/")));
    }
}
