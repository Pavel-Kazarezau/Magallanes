package io.kazarezau.magallanes.account.output.repository;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.account.output.jpa.model.UserEntity;
import io.kazarezau.magallanes.account.output.jpa.repository.UserEntityRepository;
import io.kazarezau.magallanes.account.output.mapper.UserToUserEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserEntityRepository entityRepository;
    @Mock
    private UserToUserEntityMapper userEntityMapper;
    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void findByUsername() {
        when(entityRepository.findByUsername("username")).thenReturn(Optional.of(new UserEntity()));
        final User user = new User("", "", "");
        when(userEntityMapper.userEntityToUser(any(UserEntity.class))).thenReturn(user);

        userRepository.findByUsername("username");

        verify(userEntityMapper).userEntityToUser(any(UserEntity.class));
        verify(entityRepository).findByUsername("username");
    }

    @Test
    void findByIds() {
        final UUID firstUserId = UUID.randomUUID();
        final User.UserId firstUser = new User.UserId(firstUserId);
        final UUID secondUserId = UUID.randomUUID();
        final User.UserId secondUser = new User.UserId(secondUserId);

        when(entityRepository.findAllById(List.of(firstUserId, secondUserId)))
                .thenReturn(List.of(new UserEntity(), new UserEntity()));
        when(userEntityMapper.userEntityToUser(any(UserEntity.class)))
                .thenReturn(new User("", "", ""));

        userRepository.findByIds(List.of(firstUser, secondUser));
        verify(entityRepository).findAllById(List.of(firstUserId, secondUserId));
        verify(userEntityMapper, times(2)).userEntityToUser(any(UserEntity.class));
    }

    @Test
    void save() {
        final User user = new User("", "", "");
        when(userEntityMapper.userToUserEntity(user)).thenReturn(new UserEntity());
        when(entityRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        when(userEntityMapper.userEntityToUser(any(UserEntity.class))).thenReturn(user);

        userRepository.save(user);

        verify(userEntityMapper).userToUserEntity(any(User.class));
        verify(entityRepository).save(any(UserEntity.class));
        verify(userEntityMapper).userEntityToUser(any(UserEntity.class));
    }
}