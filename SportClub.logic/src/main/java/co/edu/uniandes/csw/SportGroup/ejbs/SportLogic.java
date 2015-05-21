package co.edu.uniandes.csw.SportGroup.ejbs;

import co.edu.uniandes.csw.SportGroup.entities.CountryEntity;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.uniandes.csw.SportGroup.api.ISportLogic;
import co.edu.uniandes.csw.SportGroup.dtos.SportDTO;
import co.edu.uniandes.csw.SportGroup.converters.SportConverter;
import co.edu.uniandes.csw.SportGroup.entities.SportEntity;
import java.util.List;
import javax.persistence.Query;

@Stateless
@LocalBean
public class SportLogic extends CrudLogic<SportEntity> implements ISportLogic {

    public SportLogic() {
        entityClass = SportEntity.class;
    }

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
        return count();
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
        delete(id);
    }

    public SportDTO updateSport(SportDTO sport) {
        SportEntity entity = entityManager.merge(SportConverter.persistenceDTO2Entity(sport));
        CountryEntity country = this.getSelectedCountry(sport);
        if (country != null) {
            entity.setCountry(country);
        }
        return SportConverter.entity2PersistenceDTO(entity);
    }

    private CountryEntity getSelectedCountry(SportDTO sport) {
        if (sport != null && sport.getCountry() != null && sport.getCountry() != null) {
            return entityManager.find(CountryEntity.class, sport.getCountry());
        } else {
            return null;
        }
    }
}
