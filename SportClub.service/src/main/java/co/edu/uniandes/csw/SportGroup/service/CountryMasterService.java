package co.edu.uniandes.csw.SportGroup.service;

import co.edu.uniandes.csw.SportGroup.country.logic.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/countries/master/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryMasterService {
    
    @Inject
    protected ICountryLogic countryLogic;
    
    @GET
    public CountryDTO getCountry(@PathParam("id") Long id){
        return countryLogic.getCountryMaster(id);
    }
    
    @PUT
    public CountryDTO updateCountry(@PathParam("id") Long id, CountryDTO dto){
        dto.setId(id);
        return countryLogic.updateCountryMaster(dto);
    }
}
