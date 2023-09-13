package com.store.book.mapper;

import com.store.book.config.MapperConfig;
import com.store.book.dto.user.UserResponseDto;
import com.store.book.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponse(User user);

}
