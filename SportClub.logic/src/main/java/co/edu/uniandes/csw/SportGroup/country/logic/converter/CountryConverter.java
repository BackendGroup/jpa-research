package co.edu.uniandes.csw.SportGroup.country.logic.converter;

import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.country.logic.entity.CountryEntity;
import co.edu.uniandes.csw.SportGroup.sport.logic.converter.SportConverter;
import java.util.ArrayList;
import java.util.List;

public class CountryConverter {

    public static CountryDTO entity2PersistenceDTO(CountryEntity entity) {
        if (entity != null) {
            CountryDTO dto = new CountryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPopulation(entity.getPopulation());
            return dto;
        } else {
            return null;
        }
    }

    public static CountryEntity persistenceDTO2Entity(CountryDTO dto) {
        if (dto != null) {
            CountryEntity entity = new CountryEntity();
            entity.setId(dto.getId());

            entity.setName(dto.getName());

            entity.setPopulation(dto.getPopulation());

            return entity;
        } else {
            return null;
        }
    }

    public static CountryDTO entityMaster2PersistenceDTO(CountryEntity entity) {
        if (entity != null) {
            CountryDTO dto = entity2PersistenceDTO(entity);
            dto.setSports(SportConverter.entity2PersistenceDTOList(entity.getSports()));
            dto.setOwnedSports(SportConverter.entity2PersistenceDTOList(entity.getOwnedSports()));
            return dto;
        } else {
            return null;
        }
    }

    public static CountryEntity persistenceDTO2EntityMaster(CountryDTO dto) {
        if (dto != null) {
            CountryEntity entity = persistenceDTO2Entity(dto);
            entity.setSports(SportConverter.persistenceDTO2EntityList(dto.getSports()));
            entity.setOwnedSports(SportConverter.persistenceDTO2EntityListChild(dto.getOwnedSports(), entity));
            return entity;
        } else {
            return null;
        }
    }

    public static List<CountryDTO> entity2PersistenceDTOList(List<CountryEntity> entities) {
        List<CountryDTO> dtos = new ArrayList<CountryDTO>();
        if (entities != null) {
            for (CountryEntity entity : entities) {
                dtos.add(entity2PersistenceDTO(entity));
            }
        }
        return dtos;
    }

    public static List<CountryEntity> persistenceDTO2EntityList(List<CountryDTO> dtos) {
        List<CountryEntity> entities = new ArrayList<CountryEntity>();
        if (dtos != null) {
            for (CountryDTO dto : dtos) {
                entities.add(persistenceDTO2Entity(dto));
            }
        }
        return entities;
    }
}
