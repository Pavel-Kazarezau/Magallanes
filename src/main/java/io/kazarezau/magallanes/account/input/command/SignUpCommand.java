package io.kazarezau.magallanes.account.input.command;

import lombok.Data;

@Data
public class SignUpCommand {

    private String username;

    private String email;

    private String password;
}
