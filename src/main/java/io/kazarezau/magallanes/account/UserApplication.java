package io.kazarezau.magallanes.account;


import io.kazarezau.magallanes.account.input.command.SignInCommand;
import io.kazarezau.magallanes.account.input.command.SignUpCommand;
import io.kazarezau.magallanes.account.output.repository.UserRepository;
import io.kazarezau.magallanes.account.token.JwtService;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.Application;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Application
@Service
@RequiredArgsConstructor
public class UserApplication {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(SignUpCommand command) {
        final String username = command.getUsername();
        final String password = passwordEncoder.encode(command.getPassword());
        final String email = command.getEmail();

        final User savedUser = userRepository.save(new User(username, email, password));

        return jwtService.generateToken(savedUser);
    }

    @Transactional
    public String signIn(SignInCommand command) {
        final String username = command.getUsername();
        final String password = command.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final User user = userRepository.findByUsername(username);
        return jwtService.generateToken(user);
    }
}
