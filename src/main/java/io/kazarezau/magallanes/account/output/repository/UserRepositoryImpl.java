package io.kazarezau.magallanes.account.output.repository;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.account.output.jpa.model.UserEntity;
import io.kazarezau.magallanes.account.output.jpa.repository.UserEntityRepository;
import io.kazarezau.magallanes.account.output.mapper.UserToUserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserEntityRepository entityRepository;
    private final UserToUserEntityMapper userEntityMapper;

    @Override
    public User findByUsername(final String username) {
        return entityRepository.findByUsername(username)
                .map(userEntityMapper::userEntityToUser)
                .orElseThrow();
    }

    @Override
    public List<User> findByIds(final Collection<User.UserId> ids) {
        final List<UUID> userIds = ids.stream().map(User.UserId::id).toList();
        return entityRepository.findAllById(userIds)
                .stream()
                .map(userEntityMapper::userEntityToUser)
                .toList();
    }

    @Override
    public User save(final User user) {
        final UserEntity userEntity = userEntityMapper.userToUserEntity(user);

        final UserEntity saved = entityRepository.save(userEntity);

        return userEntityMapper.userEntityToUser(saved);
    }
}
