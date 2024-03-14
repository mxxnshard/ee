package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.dao.model.UserModel;
import web.service.converter.Converter;
import web.service.digest.Digest;
import web.service.dto.UserDto;
import web.service.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final Digest digest;
    private final Converter<UserDto, UserModel> converterDto2Model;
    private final Converter<UserModel, UserDto> converterModel2Dto;

    @Transactional
    public boolean register(UserDto userDto, String pass) {
        Optional<UserModel> fromDb = userDao.getUserByEmail(userDto.getEmail());
        if (fromDb.isPresent()) {
            return false;
        }

        String hash = digest.hex(pass);
        UserModel userModel = converterDto2Model.convert(userDto);
        userModel.setPass(hash);
        userModel.setId(UUID.randomUUID().toString());

        return userDao.insertUser(userModel);
    }

    public UserDto login(String email, String pass) {
        Optional<UserModel> userModel = userDao.getUserByEmail(email);
        UserModel user = userModel.orElseThrow(() -> new CustomException("No user found"));
        String hash = digest.hex(pass);
        if (!user.getPass().equals(hash)) {
            throw new CustomException("pass mismatch");
        }
        return converterModel2Dto.convert(user);
    }

    public List<UserDto> getUsersByFirsName(String name) {
        return userDao.getUserByName(name).stream()
                .map(converterModel2Dto::convert)
                .collect(Collectors.toList());
    }


}
