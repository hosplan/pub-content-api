package com.iuni.content.service;

import com.iuni.content.domain.Member;
import com.iuni.content.helper.jwt.CustomUserDetails;
import com.iuni.content.repository.JwtRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtService")
public class JwtService implements UserDetailsService {
    private final JwtRepository jwtRepository;
    public JwtService(JwtRepository jwtRepository){
        this.jwtRepository = jwtRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) {
        System.out.println("email = " + email);
        Member member = jwtRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("잘못된 인증 입니다.")
        );
        System.out.println("member.getRoles() = " + member.getRoles());
        System.out.println("member.getId() = " + member.getId());
        return new CustomUserDetails(member);
    }
}
