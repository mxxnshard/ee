package web.service.converter;

import org.springframework.stereotype.Service;
import web.dao.model.TypeModel;
import web.dao.model.UserModel;
import web.service.dto.TypeDto;
import web.service.dto.UserDto;
@Service
public class TypeDtoToModel  implements Converter<TypeDto, TypeModel> {
    @Override
    public TypeModel convert(TypeDto dto) {
        return new TypeModel(
                null,
                dto.getName(),
                null
        );
    }
}
