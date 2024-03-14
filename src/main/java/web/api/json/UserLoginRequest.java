package web.api.json;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    String email;
    String pass;
}
