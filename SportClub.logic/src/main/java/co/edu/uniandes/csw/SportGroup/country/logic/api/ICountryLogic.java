/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.SportGroup.country.logic.api;

import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import java.util.List;

/**
 *
 * @author afesguerra
 */
public interface ICountryLogic {

    public int countCountries();

    public CountryDTO createCountry(CountryDTO detail);

    public List<CountryDTO> getCountries(Integer page, Integer maxRecords);

    public CountryDTO getCountry(Long id);

    public void deleteCountry(Long id);

    public CountryDTO updateCountry(CountryDTO detail);

    public CountryDTO getMostPopulated();

    public CountryDTO getLeastPopulated();

    public CountryDTO getCountryMaster(Long id);

    public CountryDTO updateCountryMaster(CountryDTO dto);

    public CountryDTO createCountryMaster(CountryDTO country);

    public List<SportDTO> getCountrySports(Long id);

    public List<SportDTO> getCountryOwnedSports(Long id);
}
