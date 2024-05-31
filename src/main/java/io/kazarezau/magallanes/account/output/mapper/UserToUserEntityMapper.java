package io.kazarezau.magallanes.account.output.mapper;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.account.output.jpa.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserToUserEntityMapper {

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "email", source = "email.email")
    UserEntity userToUserEntity(User user);

    @Mapping(target = "id.id", source = "id")
    User userEntityToUser(UserEntity user);
}
