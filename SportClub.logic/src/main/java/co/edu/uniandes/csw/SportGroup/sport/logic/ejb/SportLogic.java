package co.edu.uniandes.csw.SportGroup.sport.logic.ejb;

import co.edu.uniandes.csw.SportGroup.country.logic.entity.CountryEntity;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.SportGroup.sport.logic.api.ISportLogic;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import co.edu.uniandes.csw.SportGroup.sport.logic.converter.SportConverter;
import co.edu.uniandes.csw.SportGroup.sport.logic.entity.SportEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Default
@Stateless
@LocalBean
public class SportLogic implements ISportLogic {

    @PersistenceContext(unitName = "SportClassPU")
    protected EntityManager entityManager;

    public SportDTO createSport(SportDTO sport) {
        SportEntity entity = SportConverter.persistenceDTO2Entity(sport);
        CountryEntity country = this.getSelectedCountry(sport);
        if (country != null) {
            entity.setCountry(country);
        }
        entityManager.persist(entity);
        return SportConverter.entity2PersistenceDTO(entity);
    }

    public List<SportDTO> getSports() {
        Query q = entityManager.createQuery("select u from SportEntity u");
        return SportConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public int countSports() {
        Query count = entityManager.createQuery("select count(u) from SportEntity u");
        return Integer.parseInt(count.getSingleResult().toString());
    }

    public List<SportDTO> getSports(Integer page, Integer maxRecords) {
        Query q = entityManager.createQuery("select u from SportEntity u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return SportConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public SportDTO getSport(Long id) {
        return SportConverter.entity2PersistenceDTO(entityManager.find(SportEntity.class, id));
    }

    public void deleteSport(Long id) {
        SportEntity entity = entityManager.find(SportEntity.class, id);
        entityManager.remove(entity);
    }

    public SportDTO updateSport(SportDTO sport) {
        SportEntity entity = entityManager.merge(SportConverter.persistenceDTO2Entity(sport));
        CountryEntity country = this.getSelectedCountry(sport);
        if (country != null) {
            entity.setCountry(country);
        }
        return SportConverter.entity2PersistenceDTO(entity);
    }
    private CountryEntity getSelectedCountry(SportDTO sport){
        if (sport != null && sport.getCountry() != null && sport.getCountry() != null) {
            return entityManager.find(CountryEntity.class, sport.getCountry());
        }else{
            return null;
        }
    }
}
