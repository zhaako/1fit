package cloud.client.cloudClient.jwt.auth;


import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.Coach;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.model.roles.Role;
import cloud.client.cloudClient.repository.CoachRepository;
import cloud.client.cloudClient.repository.UserRepository;
import cloud.client.cloudClient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CoachRepository coachRepository;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse registerCoach(RegisterRequestCoach request) {
        Coach coach = new Coach();
        coach.setName(request.getName());
        coach.setUsername(request.getUsername());
        coach.setPassword(passwordEncoder.encode(request.getPassword()));
        coach.setRole(Role.COACH);
        coach.setPosition(request.getPosition());
        coach.setDescription(request.getDescription());
        userRepository.save(coach);
        var jwtToken = jwtService.generateToken(coach);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

