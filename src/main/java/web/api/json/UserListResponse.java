package web.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.service.dto.UserDto;

import java.util.List;

@AllArgsConstructor
@Data
public class UserListResponse {
    List<UserDto> users;
}
