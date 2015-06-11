package co.edu.uniandes.csw.sportclub.ejbs;

import co.edu.uniandes.csw.sportclub.api.ICountryLogic;
import co.edu.uniandes.csw.sportclub.converters.CountryConverter;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class CountryLogic extends CrudLogic<CountryEntity> implements ICountryLogic {

    public CountryLogic() {
        entityClass = CountryEntity.class;
    }

    public int countCountries() {
        return count();
    }

    public List<CountryDTO> getCountries(Integer page, Integer maxRecords) {
        return CountryConverter.listEntity2DTO(findAll(page, maxRecords));
    }

    public CountryDTO getCountry(Long id) {
        return CountryConverter.fullEntity2DTO(find(id));
    }

    public CountryDTO createCountry(CountryDTO dto) {
        CountryEntity entity = CountryConverter.fullDTO2Entity(dto);
        create(entity);
        return CountryConverter.fullEntity2DTO(entity);
    }

    public CountryDTO updateCountry(CountryDTO dto) {
        CountryEntity entity = update(CountryConverter.fullDTO2Entity(dto));
        return CountryConverter.fullEntity2DTO(entity);
    }

    public void deleteCountry(Long id) {
        delete(id);
    }

    public CountryDTO getMostPopulated() {
        return CountryConverter.fullEntity2DTO((CountryEntity) executeSingleNamedQuery("Country.mostPopulated"));
    }

    public CountryDTO getLeastPopulated() {
        return CountryConverter.fullEntity2DTO((CountryEntity) executeSingleNamedQuery("Country.leastPopulated"));
    }
}
