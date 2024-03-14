package web.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsectResponse {
    String name;
    int size;
}
