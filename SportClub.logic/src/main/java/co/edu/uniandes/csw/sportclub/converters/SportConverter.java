package co.edu.uniandes.csw.sportclub.converters;

import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import co.edu.uniandes.csw.sportclub.dtos.SportDTO;
import co.edu.uniandes.csw.sportclub.entities.SportEntity;
import java.util.ArrayList;
import java.util.List;

public class SportConverter {

    public static SportDTO entity2PersistenceDTO(SportEntity entity) {
        if (entity != null) {
            SportDTO dto = new SportDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setMinAge(entity.getMinAge());
            dto.setMaxAge(entity.getMaxAge());
            dto.setRules(entity.getRules());
            dto.setCountry(CountryConverter.entity2PersistenceDTO(entity.getCountry()));
            return dto;
        } else {
            return null;
        }
    }

    public static SportEntity persistenceDTO2Entity(SportDTO dto) {
        if (dto != null) {
            SportEntity entity = new SportEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setMinAge(dto.getMinAge());
            entity.setMaxAge(dto.getMaxAge());
            entity.setRules(dto.getRules());
            entity.setCountry(CountryConverter.persistenceDTO2Entity(dto.getCountry()));

            return entity;
        } else {
            return null;
        }
    }

    public static List<SportDTO> entity2PersistenceDTOList(List<SportEntity> entities) {
        List<SportDTO> dtos = new ArrayList<SportDTO>();
        if (entities != null) {
            for (SportEntity entity : entities) {
                dtos.add(entity2PersistenceDTO(entity));
            }
        }
        return dtos;
    }

    public static List<SportEntity> persistenceDTO2EntityList(List<SportDTO> dtos) {
        List<SportEntity> entities = new ArrayList<SportEntity>();
        if (dtos != null) {
            for (SportDTO dto : dtos) {
                entities.add(persistenceDTO2Entity(dto));
            }
        }
        return entities;
    }
    
    public static SportEntity persistenceDTO2EntityChild(SportDTO dto, CountryEntity country){
        SportEntity entity = persistenceDTO2Entity(dto);
        entity.setCountry(country);
        return entity;
    }
    
    public static List<SportEntity> persistenceDTO2EntityListChild(List<SportDTO> dtos, CountryEntity country) {
        List<SportEntity> entities = new ArrayList<SportEntity>();
        if (dtos != null) {
            for (SportDTO dto : dtos) {
                entities.add(persistenceDTO2EntityChild(dto, country));
            }
        }
        return entities;
    }
}
