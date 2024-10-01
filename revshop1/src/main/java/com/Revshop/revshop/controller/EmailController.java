package com.Revshop.revshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.Revshop.revshop.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class EmailController {
	
	@Autowired
    private EmailService emailService;
	
	@PostMapping("/api/send-verification")
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendVerificationCode(@RequestParam("email") String email, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        
        // Ensure that email is not empty or null
        if (email == null || email.isEmpty()) {
            response.put("error", "Email cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Generate a random 6-digit OTP
        String verificationCode = String.format("%06d", new java.util.Random().nextInt(999999));

        try {
            // Send the verification email
            emailService.sendVerificationEmail(email, verificationCode);

            // Store the OTP in the session
            session.setAttribute("otp", verificationCode);
            session.setAttribute("email", email);

            response.put("success", "Verification code sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to send verification email.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/api/verify-otp")
    @ResponseBody
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestParam("otp") String otp, HttpSession session) {
        String savedOtp = (String) session.getAttribute("otp");

        Map<String, String> response = new HashMap<>();

        if (savedOtp != null && savedOtp.equals(otp)) {
            // OTP verified successfully
            response.put("success", "OTP verified successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid OTP!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
