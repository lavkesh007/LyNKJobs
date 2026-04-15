package com.Spring.Student.Controller;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminToken {
	private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";

	private static final SecretKey SIGN_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
	public static String Token(String userName,String userID,HttpServletResponse res) {
		String token = Jwts.builder()
						.setSubject(userName)
						.claim("adminID", userID)
						.setIssuedAt(new Date())
						.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
						.signWith(SIGN_KEY)
						.compact();
		return token;
	}
	public static Claims validateToken(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(SIGN_KEY)
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}	
	public static String getToken(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
//		if(auth == null) {
//			return null;
//		}
		if (auth != null && auth.startsWith("Bearer ")) {
	        return auth.substring(7);
	    }
		
		return null;
	}
}
