package co.edu.uniandes.csw.SportGroup.ejbs;

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
    
    public void delete(Long id) {
        Entity entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }

//    public DTO FindAll(Integer page, Integer maxRecords) {
//        Query q = entityManager.createQuery("select u from " + entityClass.getSimpleName() + " u");
//        if (page != null && maxRecords != null) {
//            q.setFirstResult((page - 1) * maxRecords);
//            q.setMaxResults(maxRecords);
//        }
//        return Converter.entity2PersistenceDTOList(q.getResultList());
//    }
}
