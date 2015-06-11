package co.edu.uniandes.csw.sportclub.ejbs;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class CrudLogic<T> {

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager em;

    protected Class<T> entityClass;

    public int count() {
        Query count = em.createQuery("select count(u) from " + entityClass.getSimpleName() + " u");
        return Integer.parseInt(count.getSingleResult().toString());
    }
    
    public T create(T entity){
        em.persist(entity);
        return entity;
    }
    
    public T update(T entity){
        return em.merge(entity);
    }

    public void delete(Long id) {
        T entity = em.find(entityClass, id);
        em.remove(entity);
    }
    
    public T find(Long id){
        return em.find(entityClass, id);
    }

    public List<T> findAll(Integer page, Integer maxRecords) {
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
