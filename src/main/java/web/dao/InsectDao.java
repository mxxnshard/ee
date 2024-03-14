package web.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.model.InsectModel;
import web.service.exception.CustomException;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsectDao {
    private final DataSource dataSource;
    @PersistenceContext
    EntityManager em;

    @Transactional
    public String insert(InsectModel model) {
        String id = UUID.randomUUID().toString();
        model.setId(id);
        em.persist(model);
        return id;
    }

    @Transactional
    public void edit(String id, InsectModel insect){
        InsectModel model = em.find(InsectModel.class, id);
        if(model == null)
            return;
        model.setName(insect.getName());
        model.setSize(insect.getSize());
        em.persist(model);
    }
    @Transactional
    public void deleteByUser(String userId){
        try {
            TypedQuery<InsectModel> query = em.createQuery("select u from InsectModel as u where id = :id", InsectModel.class);
            query.setParameter("id", userId);
            em.remove(query.getResultList().get(0));
        }
        catch (NoResultException e){
            throw new CustomException("Нет обуви");
        }
    }
    public List<InsectModel> getAll(){
        return em.createQuery("select a from InsectModel as a", InsectModel.class).getResultList();
    }
}
