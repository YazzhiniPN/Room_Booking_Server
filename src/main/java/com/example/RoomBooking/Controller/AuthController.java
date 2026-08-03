package com.example.RoomBooking.Controller;

import com.example.RoomBooking.payload.AuthRequest;
import com.example.RoomBooking.security.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);   // Not accessible via JavaScript — prevents XSS token theft
        cookie.setSecure(false);    // Set to true in production (requires HTTPS)
        cookie.setPath("/");        // Cookie sent on all paths
        cookie.setMaxAge(86400);    // 24 hours (matches jwt.expiration)
        response.addCookie(cookie);
    }

    @PostMapping("/faculty/login")
    public ResponseEntity<?> facultyLogin(@RequestBody AuthRequest req, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(user);
            setJwtCookie(response, token);
            return ResponseEntity.ok("Login successful");
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/rep/login")
    public ResponseEntity<?> repLogin(@RequestBody AuthRequest req, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(user);
            setJwtCookie(response, token);
            return ResponseEntity.ok("Login successful");
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody AuthRequest req, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(user);
            setJwtCookie(response, token);
            return ResponseEntity.ok("Login successful");
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Overwrite the cookie with an expired one to force the browser to delete it
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Immediately expires
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }
}
