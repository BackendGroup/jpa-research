package co.edu.uniandes.csw.SportGroup.country.logic.ejb;

import co.edu.uniandes.csw.SportGroup.country.logic.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.country.logic.converter.CountryConverter;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryPageDTO;
import co.edu.uniandes.csw.SportGroup.country.logic.entity.CountryEntity;
import co.edu.uniandes.csw.SportGroup.sport.logic.converter.SportConverter;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
@LocalBean
public class CountryLogic implements ICountryLogic{

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager entityManager;

    public CountryDTO createCountry(CountryDTO country) {
        CountryEntity entity = CountryConverter.persistenceDTO2Entity(country);
        entityManager.persist(entity);
        return CountryConverter.entity2PersistenceDTO(entity);
    }
    
    public int countCountries(){
        Query count = entityManager.createQuery("select count(u) from CountryEntity u");
        int regCount = Integer.parseInt(count.getSingleResult().toString());
        return regCount;
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
        return CountryConverter.entity2PersistenceDTO((CountryEntity)query.getSingleResult());
    }

    public CountryDTO getLeastPopulated() {
        Query query = entityManager.createQuery("select u from CountryEntity u WHERE u.population = (SELECT MIN(v.population) from CountryEntity v)");
        return CountryConverter.entity2PersistenceDTO((CountryEntity)query.getSingleResult());
    }

    public CountryDTO getCountryMaster(Long id) {
        return CountryConverter.entityMaster2PersistenceDTO(entityManager.find(CountryEntity.class, id));
    }    

    public CountryDTO updateCountryMaster(CountryDTO dto) {
        CountryEntity entity = entityManager.merge(CountryConverter.persistenceDTO2EntityMaster(dto));
        return CountryConverter.entityMaster2PersistenceDTO(entity);
    }

    public List<SportDTO> getCountrySports(Long id) {
        return SportConverter.entity2PersistenceDTOList(entityManager.find(CountryEntity.class, id).getSports());
    }

    public List<SportDTO> getCountryOwnedSports(Long id) {
        return SportConverter.entity2PersistenceDTOList(entityManager.find(CountryEntity.class, id).getOwnedSports());
    }
}
