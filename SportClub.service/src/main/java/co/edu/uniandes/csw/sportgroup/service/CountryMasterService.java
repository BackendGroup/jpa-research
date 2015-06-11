package co.edu.uniandes.csw.sportgroup.service;

import co.edu.uniandes.csw.sportclub.api.ICountryLogic;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
        return countryLogic.getCountry(id);
    }
    
    @PUT
    @Path("{id}")
    public CountryDTO updateCountry(@PathParam("id") Long id, CountryDTO dto){
        dto.setId(id);
        return countryLogic.updateCountry(dto);
    }
    
    @POST
    public CountryDTO createCountry(CountryDTO sport) {
        CountryDTO dto = countryLogic.createCountryMaster(sport);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(CountryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }
    
    @DELETE
    @Path("{id}")
    public void deleteCountry(@PathParam("id") Long id) {
        countryLogic.deleteCountry(id);
    }
}
