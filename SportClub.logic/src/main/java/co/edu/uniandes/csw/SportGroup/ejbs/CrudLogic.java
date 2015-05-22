package co.edu.uniandes.csw.SportGroup.ejbs;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class CrudLogic<Entity> {

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager em;

    protected Class<Entity> entityClass;

    public int count() {
        Query count = em.createQuery("select count(u) from " + entityClass.getSimpleName() + " u");
        int regCount = Integer.parseInt(count.getSingleResult().toString());
        return regCount;
    }
    
    public Entity create(Entity entity){
        em.persist(entity);
        return entity;
    }
    
    public Entity update(Entity entity){
        return em.merge(entity);
    }

    public void delete(Long id) {
        Entity entity = em.find(entityClass, id);
        em.remove(entity);
    }
    
    public Entity find(Long id){
        return em.find(entityClass, id);
    }

    public List<Entity> findAll(Integer page, Integer maxRecords) {
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
    
    public List<Object> executeListNamedQuery(String name){
        return em.createNamedQuery(name).getResultList();
    }
    public List<Object> executeListNamedQuery(String name, Map<String,Object> params){
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }
    
    public Object executeSingleNamedQuery(String name){
        return em.createNamedQuery(name).getSingleResult();
    }
    public Object executeSingleNamedQuery(String name, Map<String,Object> params){
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getSingleResult();
    }
}
