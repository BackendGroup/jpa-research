package co.edu.uniandes.csw.SportGroup.ejbs;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class CrudLogic<Entity> {

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager entityManager;

    protected Class<Entity> entityClass;

    public int count() {
        Query count = entityManager.createQuery("select count(u) from " + entityClass.getSimpleName() + " u");
        int regCount = Integer.parseInt(count.getSingleResult().toString());
        return regCount;
    }
    
    public Entity create(Entity entity){
        entityManager.persist(entity);
        return entity;
    }
    
    public Entity update(Entity entity){
        return entityManager.merge(entity);
    }

    public void delete(Long id) {
        Entity entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }
    
    public Entity find(Long id){
        return entityManager.find(entityClass, id);
    }

    public List<Entity> findAll(Integer page, Integer maxRecords) {
        Query q = entityManager.createQuery("select u from " + entityClass.getSimpleName() + " u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
}
