package net.rahuls.hitpixel.domain.user.mapper;

import net.rahuls.hitpixel.api.dto.UserDto;
import net.rahuls.hitpixel.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);
}
