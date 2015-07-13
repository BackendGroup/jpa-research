package co.edu.uniandes.csw.sportclub.persistence;

import co.edu.uniandes.csw.sportclub.entities.SportEntity;
import javax.ejb.Stateless;

@Stateless
public class SportPersistence extends CrudPersistence<SportEntity> {

    public SportPersistence() {
        this.entityClass = SportEntity.class;
    }
}
