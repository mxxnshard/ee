package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.InsectDao;
import web.dao.model.InsectModel;
import web.service.converter.InsectDtoToModelConverter;
import web.service.converter.InsectModelToDto;
import web.service.dto.InsectDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsectService {
    private final InsectDao dao;
    private final InsectDtoToModelConverter converter;
    private final InsectModelToDto converter2;

    @Transactional
    public String insert(InsectDto insectDto) {
        return dao.insert(converter.convert(insectDto));
    }
    public List<InsectModel> getAll(){
        return new ArrayList<>(dao.getAll());
    }
    public void delete(String  id){
        dao.deleteByUser(id);
    }

    public void edit(String id, InsectDto dto){
        dao.edit(id, converter.convert(dto));
    }


}
