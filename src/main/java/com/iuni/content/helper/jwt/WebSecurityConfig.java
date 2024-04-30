package com.iuni.content.helper.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JWT jwt;
    private ObjectMapper objectMapper = new ObjectMapper();
    public WebSecurityConfig(JWT jwt) {
        this.jwt = jwt;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("conts/member/**", "conts/signIn/**", "conts/trashBasket/**").permitAll()
                                .requestMatchers("conts/taskMemberMap/share", "conts/task/share", "conts/comment", "conts/comment/**").hasAuthority("USER")
                                .requestMatchers("conts/task/**", "conts/board/**", "conts/status/**", "conts/boardTaskMap/**", "conts/taskMemberMap/**").hasAnyAuthority("USER", "TEMPUSER")
                                .anyRequest().denyAll()
                )
                .addFilterBefore(new JwtAuthFilter(jwt), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e ->
                        e
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=utf-8");
                            HashMap<String, String> resultData = new HashMap<>();
                            resultData.put("token_error", "noAuth");
                            String result = objectMapper.writeValueAsString(resultData);
                            response.getWriter().write(result);
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=utf-8");
                            HashMap<String, String> resultData = new HashMap<>();
                            resultData.put("token_error", "noToken");
                            String result = objectMapper.writeValueAsString(resultData);
                            response.getWriter().write(result);
                        })
                );

        return httpSecurity.build();
    }
}
