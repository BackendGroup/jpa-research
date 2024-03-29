package co.edu.uniandes.csw.sportclub.converters;

import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class CountryConverter {

    private static CountryDTO basicEntity2DTO(CountryEntity entity) {
        if (entity != null) {
            CountryDTO dto = new CountryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPopulation(entity.getPopulation());
            dto.setFoundation(entity.getFoundation());
            return dto;
        } else {
            return null;
        }
    }

    private static CountryEntity basicDTO2Entity(CountryDTO dto) {
        if (dto != null) {
            CountryEntity entity = new CountryEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setPopulation(dto.getPopulation());
            entity.setFoundation(dto.getFoundation());
            return entity;
        } else {
            return null;
        }
    }

    public static CountryDTO fullEntity2DTO(CountryEntity entity) {
        if (entity != null) {
            CountryDTO dto = basicEntity2DTO(entity);
            dto.setSports(SportConverter.entity2PersistenceDTOList(entity.getSports()));
            dto.setOwnedSports(SportConverter.entity2PersistenceDTOList(entity.getOwnedSports()));
            return dto;
        } else {
            return null;
        }
    }

    public static CountryEntity fullDTO2Entity(CountryDTO dto) {
        if (dto != null) {
            CountryEntity entity = basicDTO2Entity(dto);
            entity.setSports(SportConverter.persistenceDTO2EntityList(dto.getSports()));
            entity.setOwnedSports(SportConverter.persistenceDTO2EntityListChild(dto.getOwnedSports(), entity));
            return entity;
        } else {
            return null;
        }
    }

    public static List<CountryDTO> listEntity2DTO(List<CountryEntity> entities) {
        List<CountryDTO> dtos = new ArrayList<CountryDTO>();
        if (entities != null) {
            for (CountryEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    public static List<CountryEntity> listDTO2Entity(List<CountryDTO> dtos) {
        List<CountryEntity> entities = new ArrayList<CountryEntity>();
        if (dtos != null) {
            for (CountryDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }

    public static CountryDTO refEntity2DTO(CountryEntity entity) {
        if (entity != null) {
            CountryDTO dto = new CountryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        } else {
            return null;
        }
    }

    public static CountryEntity refDTO2Entity(CountryDTO dto) {
        if (dto != null) {
            CountryEntity entity = new CountryEntity();
            entity.setId(dto.getId());
            return entity;
        } else {
            return null;
        }
    }
}
