package co.edu.uniandes.csw.SportGroup.ejbs;

import co.edu.uniandes.csw.SportGroup.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.converters.CountryConverter;
import co.edu.uniandes.csw.SportGroup.dtos.CountryDTO;
import co.edu.uniandes.csw.SportGroup.entities.CountryEntity;
import co.edu.uniandes.csw.SportGroup.converters.SportConverter;
import co.edu.uniandes.csw.SportGroup.dtos.SportDTO;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class CountryLogic extends CrudLogic<CountryEntity> implements ICountryLogic {

    public CountryLogic() {
        entityClass = CountryEntity.class;
    }

    public CountryDTO createCountry(CountryDTO dto) {
        CountryEntity entity = CountryConverter.persistenceDTO2Entity(dto);
        create(entity);
        return CountryConverter.entity2PersistenceDTO(entity);
    }

    public int countCountries() {
        return count();
    }

    public List<CountryDTO> getCountries(Integer page, Integer maxRecords) {
        return CountryConverter.entity2PersistenceDTOList(findAll(page, maxRecords));
    }

    public CountryDTO getCountry(Long id) {
        return CountryConverter.entity2PersistenceDTO(find(id));
    }

    public void deleteCountry(Long id) {
        delete(id);
    }

    public CountryDTO updateCountry(CountryDTO dto) {
        CountryEntity entity = update(CountryConverter.persistenceDTO2Entity(dto));
        return CountryConverter.entity2PersistenceDTO(entity);
    }

    public CountryDTO getMostPopulated() {
        return CountryConverter.entity2PersistenceDTO((CountryEntity) executeSingleNamedQuery("Country.mostPopulated"));
    }

    public CountryDTO getLeastPopulated() {
        return CountryConverter.entity2PersistenceDTO((CountryEntity) executeSingleNamedQuery("Country.leastPopulated"));
    }

    public CountryDTO getCountryMaster(Long id) {
        return CountryConverter.entityMaster2PersistenceDTO(find(id));
    }

    public CountryDTO updateCountryMaster(CountryDTO dto) {
        CountryEntity entity = update(CountryConverter.persistenceDTO2EntityMaster(dto));
        return CountryConverter.entityMaster2PersistenceDTO(entity);
    }

    public CountryDTO createCountryMaster(CountryDTO dto) {
        CountryEntity entity = CountryConverter.persistenceDTO2EntityMaster(dto);
        create(entity);
        return CountryConverter.entityMaster2PersistenceDTO(entity);
    }

    public List<SportDTO> getCountrySports(Long id) {
        return SportConverter.entity2PersistenceDTOList(find(id).getSports());
    }

    public List<SportDTO> getCountryOwnedSports(Long id) {
        return SportConverter.entity2PersistenceDTOList(find(id).getOwnedSports());
    }
}
