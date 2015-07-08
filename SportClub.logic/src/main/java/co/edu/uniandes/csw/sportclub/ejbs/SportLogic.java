package co.edu.uniandes.csw.sportclub.ejbs;

import javax.ejb.Stateless;

import co.edu.uniandes.csw.sportclub.api.ISportLogic;
import co.edu.uniandes.csw.sportclub.dtos.SportDTO;
import co.edu.uniandes.csw.sportclub.converters.SportConverter;
import co.edu.uniandes.csw.sportclub.entities.SportEntity;
import java.util.List;
import javax.inject.Inject;

@Stateless
public class SportLogic implements ISportLogic {
    
    @Inject
    private CrudLogic<SportEntity> persistence;

    public SportDTO createSport(SportDTO dto) {
        SportEntity entity = SportConverter.persistenceDTO2Entity(dto);
        persistence.create(entity);
        return SportConverter.entity2PersistenceDTO(entity);
    }

    public int countSports() {
        return persistence.count();
    }

    public List<SportDTO> getSports(Integer page, Integer maxRecords) {
        return SportConverter.entity2PersistenceDTOList(persistence.findAll(page, maxRecords));
    }

    public SportDTO getSport(Long id) {
        return SportConverter.entity2PersistenceDTO(persistence.find(id));
    }

    public void deleteSport(Long id) {
        persistence.delete(id);
    }

    public SportDTO updateSport(SportDTO dto) {
        SportEntity entity = persistence.update(SportConverter.persistenceDTO2Entity(dto));
        return SportConverter.entity2PersistenceDTO(entity);
    }
    
    public List<SportDTO> searchByName(String name){
        return SportConverter.entity2PersistenceDTOList(persistence.findByName(name));
    }
}
