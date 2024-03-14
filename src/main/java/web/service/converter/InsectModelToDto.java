package web.service.converter;

import org.springframework.stereotype.Service;
import web.dao.model.InsectModel;
import web.service.dto.InsectDto;
@Service
public class InsectModelToDto implements Converter<InsectModel, InsectDto> {
    @Override
    public InsectDto convert(InsectModel dto) {
        return new InsectDto(
                dto.getUserId(),
                dto.getName(),
                dto.getSize()
        );
    }
}