package web.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.model.TypeModel;
import web.service.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeDao {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void insert(TypeModel model) {
        em.persist(model);
    }

    public List<TypeModel> getAll() {
        return em.createQuery("select s from TypeModel s", TypeModel.class)
                .getResultList();
    }
    @Transactional
    public void edit(String id, TypeModel model){
        try {
            TypedQuery<TypeModel> query = em.createQuery("select u from TypeModel as u where id = :id", TypeModel.class);
            query.setParameter("id", id);
            TypeModel newModel = query.getResultList().get(0);
            newModel.setName(model.getName());
            em.persist(newModel);
        } catch (NoResultException e){
            throw new CustomException("Нет такого типа");
        }
    }
    @Transactional
    public void delete(String name){
        TypedQuery<TypeModel> query = em.createQuery("select u from TypeModel as u where id = :name", TypeModel.class);
        query.setParameter("name", name);
        em.remove(query.getSingleResult());

    }
    public Optional<TypeModel> get(String id) {
        return Optional.ofNullable(em.find(TypeModel.class, id));
    }
}
