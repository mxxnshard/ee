package web.service.converter;

import org.springframework.stereotype.Service;
import web.dao.model.UserModel;
import web.service.dto.UserDto;
@Service
public class UserModelToDtoConverter implements Converter<UserModel, UserDto> {
    @Override
    public UserDto convert(UserModel userModel) {
        return new UserDto(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getSecondName(),
                userModel.getEmail()
        );
    }
}
