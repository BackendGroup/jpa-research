package co.edu.uniandes.csw.SportGroup.ejbs;

import javax.ejb.Stateless;

import co.edu.uniandes.csw.SportGroup.api.ISportLogic;
import co.edu.uniandes.csw.SportGroup.dtos.SportDTO;
import co.edu.uniandes.csw.SportGroup.converters.SportConverter;
import co.edu.uniandes.csw.SportGroup.entities.SportEntity;
import java.util.List;

@Stateless
public class SportLogic extends CrudLogic<SportEntity> implements ISportLogic {

    public SportLogic() {
        entityClass = SportEntity.class;
    }

    public SportDTO createSport(SportDTO dto) {
        SportEntity entity = SportConverter.persistenceDTO2Entity(dto);
        create(entity);
        return SportConverter.entity2PersistenceDTO(entity);
    }

    public int countSports() {
        return count();
    }

    public List<SportDTO> getSports(Integer page, Integer maxRecords) {
        return SportConverter.entity2PersistenceDTOList(findAll(page, maxRecords));
    }

    public SportDTO getSport(Long id) {
        return SportConverter.entity2PersistenceDTO(find(id));
    }

    public void deleteSport(Long id) {
        delete(id);
    }

    public SportDTO updateSport(SportDTO dto) {
        SportEntity entity = update(SportConverter.persistenceDTO2Entity(dto));
        return SportConverter.entity2PersistenceDTO(entity);
    }
}
