package co.edu.uniandes.csw.sportclub.ejbs;

import co.edu.uniandes.csw.sportclub.api.ICountryLogic;
import co.edu.uniandes.csw.sportclub.converters.CountryConverter;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CountryLogic implements ICountryLogic {
    
    @Inject
    private CrudLogic<CountryEntity> persistence;

    public int countCountries() {
        return persistence.count();
    }

    public List<CountryDTO> getCountries(Integer page, Integer maxRecords) {
        return CountryConverter.listEntity2DTO(persistence.findAll(page, maxRecords));
    }

    public CountryDTO getCountry(Long id) {
        return CountryConverter.fullEntity2DTO(persistence.find(id));
    }

    public CountryDTO createCountry(CountryDTO dto) {
        CountryEntity entity = CountryConverter.fullDTO2Entity(dto);
        persistence.create(entity);
        return CountryConverter.fullEntity2DTO(entity);
    }

    public CountryDTO updateCountry(CountryDTO dto) {
        CountryEntity entity = persistence.update(CountryConverter.fullDTO2Entity(dto));
        return CountryConverter.fullEntity2DTO(entity);
    }

    public void deleteCountry(Long id) {
        persistence.delete(id);
    }

    public CountryDTO getMostPopulated() {
        return CountryConverter.fullEntity2DTO((CountryEntity) persistence.executeSingleNamedQuery("Country.mostPopulated"));
    }

    public CountryDTO getLeastPopulated() {
        return CountryConverter.fullEntity2DTO((CountryEntity) persistence.executeSingleNamedQuery("Country.leastPopulated"));
    }
}
