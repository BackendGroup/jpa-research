package co.edu.uniandes.csw.SportGroup.ejbs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class CrudLogic {

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager entityManager;
    
    protected Class entityClass;
    protected Class dtoClass;

    public int count() {
        Query count = entityManager.createQuery("select count(u) from "+ entityClass.getSimpleName() +" u");
        int regCount = Integer.parseInt(count.getSingleResult().toString());
        return regCount;
    }
}
