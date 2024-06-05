package io.kazarezau.magallanes.account;

import io.kazarezau.magallanes.account.output.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsername() {
        userService.loadUserByUsername("username");
        verify(userRepository).findByUsername("username");
    }

    @Test
    void getUsersByIds() {
        final Set<User.UserId> ids = Set.of(new User.UserId(UUID.randomUUID()));

        userService.getUsersByIds(ids);
        
        verify(userRepository).findByIds(ids);
    }
}