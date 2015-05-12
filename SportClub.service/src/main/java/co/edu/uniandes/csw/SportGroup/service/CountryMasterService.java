package co.edu.uniandes.csw.SportGroup.service;

import co.edu.uniandes.csw.SportGroup.country.logic.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/countries/master")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryMasterService {
    
    @Inject
    protected ICountryLogic countryLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;
    
    @GET
    public List<CountryDTO> getCountries(){
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", countryLogic.countCountries());
        }
        return countryLogic.getCountries(page, maxRecords);
    }
    
    @GET
    @Path("{id}")
    public CountryDTO getCountry(@PathParam("id") Long id){
        return countryLogic.getCountryMaster(id);
    }
    
    @PUT
    @Path("{id}")
    public CountryDTO updateCountry(@PathParam("id") Long id, CountryDTO dto){
        dto.setId(id);
        return countryLogic.updateCountryMaster(dto);
    }
}
