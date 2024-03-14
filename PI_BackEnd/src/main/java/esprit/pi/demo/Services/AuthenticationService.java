package esprit.pi.demo.Services;

import esprit.pi.demo.DTO.AuthenticationRequest;
import esprit.pi.demo.DTO.AuthenticationResponse;
import esprit.pi.demo.DTO.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request) ;
    public AuthenticationResponse authenticate(AuthenticationRequest request) ;
}
