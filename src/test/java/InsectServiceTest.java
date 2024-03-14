import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import web.dao.InsectDao;
import web.dao.model.InsectModel;
import web.service.InsectService;
import web.service.converter.InsectDtoToModelConverter;
import web.service.converter.InsectModelToDto;
import web.service.dto.InsectDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InsectServiceTest {
    @InjectMocks
    InsectService service;
    @Mock
    InsectDao dao;
    @Spy
    InsectDtoToModelConverter converter = new InsectDtoToModelConverter();

    @Spy
    InsectModelToDto converter2 = new InsectModelToDto();
    @BeforeEach
    void setUp(){

    }
    @Test
    void insert(){
        String name = "насекомые";
        String userId = "userId";
        String uuid = UUID.randomUUID().toString();
        InsectDto dto = new InsectDto(userId, name, 4);
        InsectModel model = new InsectModel(uuid, userId, null, null, name, 4);
        when(converter.convert(dto)).thenReturn(model);
        service.insert(dto);
        verify(converter, times(1)).convert(dto);
        verify(dao, times(1)).insert(model);
    }

    @Test
    void edit(){
        String name = "насекомые";
        String userId = "userId";
        String uuid = UUID.randomUUID().toString();
        InsectDto dto = new InsectDto(userId, name, 4);
        InsectModel model = new InsectModel(uuid, userId, null, null, name, 4);
        when(converter.convert(dto)).thenReturn(model);
        service.edit(uuid, dto);
        verify(converter, times(1)).convert(dto);
        verify(dao, times(1)).edit(uuid, model);
    }

    @Test
    void delete(){
        String uuid = UUID.randomUUID().toString();
        service.delete(uuid);
        verify(dao, times(1)).deleteByUser(uuid);
    }

    @Test
    void getAll(){
        List<InsectModel> list = new ArrayList<>();
        List<InsectDto> list1 = new ArrayList<>();
        when(dao.getAll()).thenReturn(list);
        assertEquals(list1, service.getAll());
        verify(dao, times(1)).getAll();
    }
}
