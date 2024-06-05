package io.kazarezau.magallanes.email;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.account.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEmailExtractorTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserEmailExtractor emailExtractor;

    @Test
    void shouldExtract() {
        final User.UserId firstUserId = new User.UserId(UUID.randomUUID());
        final User.UserId secondUserId = new User.UserId(UUID.randomUUID());
        final Set<User.UserId> userIds = Set.of(firstUserId, secondUserId);

        final User firstUser = new User("John", "john@mail.com", "-");
        final User secondUser = new User("Michael", "michael@mail.com", "-");

        when(userService.getUsersByIds(userIds)).thenReturn(List.of(firstUser, secondUser));

        final List<String> expected = List.of("john@mail.com", "michael@mail.com");
        final List<String> emails = emailExtractor.getEmails(userIds);
        assertEquals(expected, emails);
    }
}