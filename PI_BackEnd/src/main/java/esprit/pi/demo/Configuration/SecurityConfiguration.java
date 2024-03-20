package esprit.pi.demo.Configuration;

import esprit.pi.demo.Security.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static esprit.pi.demo.entities.Enumeration.Permission.*;
import static esprit.pi.demo.entities.Enumeration.Role.ADMIN;
import static esprit.pi.demo.entities.Enumeration.Role.CLIENT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers("/user/**").hasAnyRole(ADMIN.name(),CLIENT.name())

                                .requestMatchers(GET,"/user/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(POST,"/user/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                                .requestMatchers(PUT,"/user/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                                .requestMatchers(DELETE,"/user/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())
                                .requestMatchers(PATCH,"/user/**").hasAnyAuthority(ADMIN_PATCH.name(),USER_PATCH.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                                .requestMatchers(GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers(PATCH,"/api/v1/admin/**").hasAuthority(ADMIN_PATCH.name())



                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logout)->logout.logoutUrl("/api/v1/auth/logout"))
                .logout((logout)->logout.addLogoutHandler(logoutHandler))
                .logout((logout)->logout.logoutSuccessHandler((request, response, authentication) ->
                       SecurityContextHolder.clearContext() ))

        ;

        return http.build();
    }

}
