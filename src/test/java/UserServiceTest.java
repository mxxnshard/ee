import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import web.dao.UserDao;
import web.dao.model.UserModel;
import web.service.UserService;
import web.service.converter.UserDtoToModelConverter;
import web.service.converter.UserModelToDtoConverter;
import web.service.digest.Digest;
import web.service.digest.DigestMd5;
import web.service.dto.UserDto;
import web.service.exception.CustomException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService subj;
    @Mock UserDao userDao;
    @Spy UserDtoToModelConverter converterDto2Model = new UserDtoToModelConverter();
    @Spy UserModelToDtoConverter converterModel2Dto = new UserModelToDtoConverter();
    @Mock Digest digest = new DigestMd5();

    @BeforeEach
    void setUp() {

    }

    @Test
    void insertUser() {
        String pass = "pass";
        String hash = "hash";
        String uuid = UUID.randomUUID().toString();

        when(digest.hex(pass)).thenReturn(hash);

        UserDto userDto = new UserDto(uuid, "name", "second", "email");
        UserModel userModel = new UserModel(uuid, "name", "second", "mail", pass, null);

        when(converterDto2Model.convert(userDto)).thenReturn(userModel);
        when(userDao.insertUser(userModel)).thenReturn(true);

        boolean b = subj.register(userDto, pass);

        assertTrue(b);

        verify(converterDto2Model, times(1)).convert(userDto);
        verify(userDao, times(1)).insertUser(userModel);
    }

    @Test
    void login_success() {
        String pass = "pass";
        String mail = "mail";
        String hash = "hash";
        final String uuid = UUID.randomUUID().toString();
        UserModel userModel = new UserModel(uuid, "name", "second", "mail", hash, null);
        UserDto userDto = new UserDto(uuid, "name", "second", "mail");

        when(userDao.getUserByEmail(mail)).thenReturn(Optional.of(userModel));
        when(digest.hex(pass)).thenReturn(hash);

        final UserDto user = subj.login(mail, pass);

        assertEquals(userDto, user);

        verify(userDao, times(1)).getUserByEmail(mail);
        verify(digest, times(1)).hex(pass);
        verify(converterModel2Dto, times(1)).convert(userModel);
    }

    @Test
    void login_NoUserFound() {
        String pass = "pass";
        String mail = "mail";

        when(userDao.getUserByEmail(mail)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> subj.login(mail, pass));

        verify(userDao, times(1)).getUserByEmail(mail);
    }

    @Test
    void login_passMismatch() {
        String pass = "pass";
        String mail = "mail";
        String hash = "hash";
        String incorrectHash = "qwerty";
        final String uuid = UUID.randomUUID().toString();
        UserModel userModel = new UserModel(uuid, "name", "second", "mail", incorrectHash, null);

        when(userDao.getUserByEmail(mail)).thenReturn(Optional.of(userModel));
        when(digest.hex(pass)).thenReturn(hash);

        assertThrows(CustomException.class, () -> subj.login(mail, pass));

        verify(userDao, times(1)).getUserByEmail(mail);
        verify(digest, times(1)).hex(pass);
        verifyNoInteractions(converterModel2Dto);
    }
}