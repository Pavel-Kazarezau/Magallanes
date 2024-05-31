package io.kazarezau.magallanes.email;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.account.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEmailExtractor {

    private final UserService userService;

    public List<String> getEmails(Set<User.UserId> userIds) {
        final List<User> usersByIds = userService.getUsersByIds(userIds);
        return usersByIds.stream().map(User::getEmail).map(User.Email::email).toList();
    }

}
