package esprit.pi.demo.Configuration;

import esprit.pi.demo.Services.IServiceUser;
import esprit.pi.demo.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class BanFilter extends OncePerRequestFilter {
    private IServiceUser serviceUser;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        User userPrincipal =  serviceUser.getCurrentUser(request.getUserPrincipal());

        if (userPrincipal != null && userPrincipal.isBanni()&& !request.getRequestURI().equals("/api/v1/auth/**")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Vous avez été banni. Accès refusé.");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
