package co.edu.uniandes.csw.SportGroup.ejbs;

import co.edu.uniandes.csw.SportGroup.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.converters.CountryConverter;
import co.edu.uniandes.csw.SportGroup.dtos.CountryDTO;
import co.edu.uniandes.csw.SportGroup.entities.CountryEntity;
import co.edu.uniandes.csw.SportGroup.converters.SportConverter;
import co.edu.uniandes.csw.SportGroup.dtos.SportDTO;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CountryLogic extends CrudLogic implements ICountryLogic {

    public CountryLogic() {
        entityClass = CountryEntity.class;
        dtoClass = CountryDTO.class;
    }

    public CountryDTO createCountry(CountryDTO country) {
        CountryEntity entity = CountryConverter.persistenceDTO2Entity(country);
        entityManager.persist(entity);
        return CountryConverter.entity2PersistenceDTO(entity);
    }

    public int countCountries() {
        return count();
    }

    public List<CountryDTO> getCountries(Integer page, Integer maxRecords) {
        Query q = entityManager.createQuery("select u from CountryEntity u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return CountryConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public CountryDTO getCountry(Long id) {
        return CountryConverter.entity2PersistenceDTO(entityManager.find(CountryEntity.class, id));
    }

    public void deleteCountry(Long id) {
        CountryEntity entity = entityManager.find(CountryEntity.class, id);
        entityManager.remove(entity);
    }

    public CountryDTO updateCountry(CountryDTO country) {
        CountryEntity entity = entityManager.merge(CountryConverter.persistenceDTO2Entity(country));
        return CountryConverter.entity2PersistenceDTO(entity);
    }

    public CountryDTO getMostPopulated() {
        Query query = entityManager.createQuery("select u from CountryEntity u WHERE u.population = (SELECT MAX(v.population) from CountryEntity v)");
        return CountryConverter.entity2PersistenceDTO((CountryEntity) query.getSingleResult());
    }

    public CountryDTO getLeastPopulated() {
        Query query = entityManager.createQuery("select u from CountryEntity u WHERE u.population = (SELECT MIN(v.population) from CountryEntity v)");
        return CountryConverter.entity2PersistenceDTO((CountryEntity) query.getSingleResult());
    }

    public CountryDTO getCountryMaster(Long id) {
        return CountryConverter.entityMaster2PersistenceDTO(entityManager.find(CountryEntity.class, id));
    }

    public CountryDTO updateCountryMaster(CountryDTO dto) {
        CountryEntity entity = entityManager.merge(CountryConverter.persistenceDTO2EntityMaster(dto));
        return CountryConverter.entityMaster2PersistenceDTO(entity);
    }

    public CountryDTO createCountryMaster(CountryDTO country) {
        CountryEntity entity = CountryConverter.persistenceDTO2EntityMaster(country);
        entityManager.persist(entity);
        return CountryConverter.entityMaster2PersistenceDTO(entity);
    }

    public List<SportDTO> getCountrySports(Long id) {
        return SportConverter.entity2PersistenceDTOList(entityManager.find(CountryEntity.class, id).getSports());
    }

    public List<SportDTO> getCountryOwnedSports(Long id) {
        return SportConverter.entity2PersistenceDTOList(entityManager.find(CountryEntity.class, id).getOwnedSports());
    }
}
