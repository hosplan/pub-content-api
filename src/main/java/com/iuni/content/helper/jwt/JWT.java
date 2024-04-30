package com.iuni.content.helper.jwt;
import com.iuni.content.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWT {
    private static final String SECRET_KEY = "dkdldbslqjtm20231230wjdtlrdhvmsdmfgidgotjekffurksek";
    //private final long exp = 1000L * 60 * 60;
    private final JwtService jwtService;
    public JWT(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getAccount(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    public String getNickName(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("nickName", String.class);

    }

    public Authentication getAuth(String token){
        UserDetails userDetails = jwtService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolve(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validate(String token){
        try{

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());


        }
        catch(Exception e){
            return false;
        }
    }
}
