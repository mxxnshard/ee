package web.service.converter;

import org.springframework.stereotype.Service;
import web.dao.model.InsectModel;
import web.service.dto.InsectDto;

@Service
public class InsectDtoToModelConverter implements Converter<InsectDto, InsectModel> {
    @Override
    public InsectModel convert(InsectDto dto) {
        return new InsectModel(
                null,
                dto.getUserId(),
                null,
                null,
                dto.getName(),
                dto.getSize()
        );
    }
}
