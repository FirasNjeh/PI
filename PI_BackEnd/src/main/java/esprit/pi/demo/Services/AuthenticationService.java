package esprit.pi.demo.Services;

import esprit.pi.demo.DTO.AuthenticationRequest;
import esprit.pi.demo.DTO.AuthenticationResponse;
import esprit.pi.demo.DTO.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
     AuthenticationResponse register(RegisterRequest request) ;
     AuthenticationResponse authenticate(AuthenticationRequest request) ;
     void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
