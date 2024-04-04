package esprit.pi.demo.Security.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import esprit.pi.demo.DTO.AuthenticationRequest;
import esprit.pi.demo.DTO.AuthenticationResponse;
import esprit.pi.demo.DTO.RegisterRequest;
import esprit.pi.demo.Repository.PortefeuilleRepository;
import esprit.pi.demo.Repository.TokenRepository;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.Security.Jwt.JwtService;
//import esprit.pi.demo.Services.EmailService;
import esprit.pi.demo.Services.PortefeuilleService;
import esprit.pi.demo.entities.Portefeuille;
import esprit.pi.demo.entities.Token;
import esprit.pi.demo.entities.Enumeration.TokenType;
import esprit.pi.demo.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

@Service
@AllArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PortefeuilleRepository portefeuilleRepository;
    private final PortefeuilleService portefeuilleService;
//    private EmailService emailService;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .mdp(passwordEncoder.encode(request.getMdp()))
                .role(request.getRole())
                .cin(request.getCin())
                .dateNaissance(request.getDateNaissance())
                .numtel(request.getNumtel())
                .adresse(request.getAdresse())
                .profession(request.getProfession())
                .genre(request.getGenre())
                .salaire(request.getSalaire())
                .age(calculateAge(request.getDateNaissance()))
                .banni(false)
                .build();

        var savedUser = userRepository.save(user);
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setUser(savedUser);
        portefeuilleRepository.save(portefeuille);
        portefeuille=portefeuilleService.getPortefeuilleById(portefeuille.getId());
        savedUser.setPortefeuilleUser(portefeuille);
        userRepository.save(savedUser);
        String to = savedUser.getEmail(); // replace with the actual recipient email
        String subject = "Confirmation d'inscription";
        String body = "Bonjour " + savedUser.getNom() + ",\n\nVotre inscription à notre site FundHub a été confirmée avec succès.";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace(); // handle the exception according to your application's needs
        }

//        String to = savedUser.getEmail();
//        String subject = "Confirmation d'inscription";
//        String text = "Bonjour " + savedUser.getNom() + ",\n\nVotre inscription à notre site FundHub a été confirmée avec succès.";
//        emailService.sendEmail(to, subject, text);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        var refreshToken =jwtService.generateRefreshToken(user) ;
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
          t.setExpired(true);
          t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .userToken(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getMdp())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        var refreshToken =jwtService.generateRefreshToken(user) ;
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        final String authHeader= request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;
        if(authHeader== null ||!authHeader.startsWith("Bearer ")) {

            return;
        }
        refreshToken=authHeader.substring(7);
        userEmail= jwtService.extractUsername(refreshToken);
        if(userEmail != null){
             var  userDetails = this.userRepository.findByEmail(userEmail)
                     .orElseThrow();
            if (jwtService.isTokenValid(refreshToken,userDetails)){
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails,accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }
    private int calculateAge(LocalDate dateNaissance) {
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - dateNaissance.getYear();
        if (dateNaissance.getMonthValue() > currentDate.getMonthValue() ||
                (dateNaissance.getMonthValue() == currentDate.getMonthValue() &&
                        dateNaissance.getDayOfMonth() > currentDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }
    private void sendEmail(String to, String subject, String body) throws MessagingException {
        // Send an email
        // ...
        String from = "njehfiras@gmail.com";
        String password = "breu joma uzhw yipn";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        //   message.setText(body);
        message.setContent(body, "text/html");

        Transport.send(message);
    }
}
