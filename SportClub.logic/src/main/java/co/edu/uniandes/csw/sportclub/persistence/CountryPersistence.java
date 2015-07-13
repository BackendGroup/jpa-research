package co.edu.uniandes.csw.sportclub.persistence;

import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import javax.ejb.Stateless;

@Stateless
public class CountryPersistence extends CrudPersistence<CountryEntity> {

    public CountryPersistence() {
        this.entityClass = CountryEntity.class;
    }
}
