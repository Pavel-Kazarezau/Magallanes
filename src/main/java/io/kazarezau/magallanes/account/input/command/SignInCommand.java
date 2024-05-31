package io.kazarezau.magallanes.account.input.command;

import lombok.Data;

@Data
public class SignInCommand {

    private String username;

    private String password;

}
