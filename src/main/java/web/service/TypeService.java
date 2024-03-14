package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.dao.TypeDao;
import web.dao.model.TypeModel;
import web.service.converter.TypeDtoToModel;
import web.service.converter.TypeModelToDto;
import web.service.dto.TypeAllDto;
import web.service.dto.TypeDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeDao dao;
    private final TypeDtoToModel converter;
    private final TypeModelToDto converter2;

    public void insert(String type) {
        TypeModel model = new TypeModel(
                UUID.randomUUID().toString(),
                type, null
        );
        dao.insert(model);
    }

    public List<TypeAllDto> getAll() {
        return dao.getAll().stream().map(converter2::convert).collect(Collectors.toList());
    }
    public void edit(String id, TypeDto dto){
        dao.edit(id, converter.convert(dto));
    }
    public void delete(String name){
        dao.delete(name);
    }
}
