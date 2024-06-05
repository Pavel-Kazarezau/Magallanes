package io.kazarezau.magallanes.account;

import io.kazarezau.magallanes.account.input.command.SignInCommand;
import io.kazarezau.magallanes.account.input.command.SignUpCommand;
import io.kazarezau.magallanes.account.output.repository.UserRepository;
import io.kazarezau.magallanes.account.token.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApplicationTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserApplication userApplication;

    @Test
    void signUp() {
        final SignUpCommand command = new SignUpCommand();
        command.setUsername("username");
        command.setPassword("password");
        command.setEmail("email");

        when(userRepository.save(any(User.class))).thenReturn(new User("username", "password", "email"));

        userApplication.signUp(command);

        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    void signIn() {
        final SignInCommand command = new SignInCommand();
        command.setUsername("username");
        command.setPassword("password");

        when(userRepository.findByUsername("username"))
                .thenReturn(new User("username", "password", "email"));

        userApplication.signIn(command);

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(any(User.class));
    }
}