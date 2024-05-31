package io.kazarezau.magallanes.account.output.repository;

import io.kazarezau.magallanes.account.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {

    List<User> findByIds(Collection<User.UserId> id);

    User save(User user);

    User findByUsername(String username);
}
