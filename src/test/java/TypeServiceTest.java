import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import web.dao.TypeDao;
import web.dao.model.TypeModel;
import web.service.TypeService;
import web.service.converter.TypeDtoToModel;
import web.service.dto.TypeDto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {
    @InjectMocks
    TypeService service;
    @Mock
    TypeDao dao;
    @Spy
    TypeDtoToModel converterDto;

    @Test
    void insert(){
        service.insert("123");
        verify(dao, times(1)).insert(any());
    }

    @Test
    void delete(){
        String name = "123";
        service.delete(name);
        verify(dao, times(1)).delete(name);
    }

    @Test
    void edit(){
        String name = "123";
        TypeModel model = new TypeModel();
        TypeDto dto = new TypeDto();
        service.edit(name, dto);
        verify(dao, times(1)).edit(name, model);
    }
}
