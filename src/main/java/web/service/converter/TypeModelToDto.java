package web.service.converter;

import org.springframework.stereotype.Service;
import web.dao.model.TypeModel;
import web.service.dto.TypeAllDto;
import web.service.dto.TypeDto;
@Service
public class TypeModelToDto   implements Converter<TypeModel, TypeAllDto> {
    @Override
    public TypeAllDto convert(TypeModel model) {
        return new TypeAllDto(
                model.getId(),
                model.getName()
        );
    }
}