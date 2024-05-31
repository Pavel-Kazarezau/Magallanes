package io.kazarezau.magallanes.account.input.rest.controller.controller;

import io.kazarezau.magallanes.account.UserApplication;
import io.kazarezau.magallanes.account.input.command.SignInCommand;
import io.kazarezau.magallanes.account.input.command.SignUpCommand;
import io.kazarezau.magallanes.account.input.rest.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserApplication userApplication;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpCommand command) {
        return new JwtAuthenticationResponse(userApplication.signUp(command));
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInCommand command) {
        return new JwtAuthenticationResponse(userApplication.signIn(command));
    }
}
