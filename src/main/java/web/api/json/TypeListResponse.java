package web.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.service.dto.TypeDto;

import java.util.List;

@Data
@AllArgsConstructor
public class TypeListResponse {
    List<TypeDto> types;
}
