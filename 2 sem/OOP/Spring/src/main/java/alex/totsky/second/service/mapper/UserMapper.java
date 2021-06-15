package alex.totsky.second.service.mapper;

import alex.totsky.second.dto.user.UserReadDto;
import alex.totsky.second.dto.user.UserWriteDto;
import alex.totsky.second.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = CommonMapper.class)
public interface UserMapper extends CommonMapper<UserReadDto, UserWriteDto, User> {
}
