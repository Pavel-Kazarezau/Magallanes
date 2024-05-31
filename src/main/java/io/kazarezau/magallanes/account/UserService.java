package io.kazarezau.magallanes.account;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getUsersByIds(Set<User.UserId> ids);

}
