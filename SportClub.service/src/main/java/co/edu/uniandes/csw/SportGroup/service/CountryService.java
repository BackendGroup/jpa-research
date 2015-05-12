package co.edu.uniandes.csw.SportGroup.service;

import co.edu.uniandes.csw.SportGroup.country.logic.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryService {

    @Inject private ICountryLogic countryLogic;
    @Context private HttpServletResponse response;
    @HeaderParam("page") private Integer page;
    @HeaderParam("maxRecords") private Integer maxRecords;

    @POST
    public CountryDTO createCountry(CountryDTO sport) {
        CountryDTO dto = countryLogic.createCountry(sport);
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

    @GET
    public List<CountryDTO> getCountries() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", countryLogic.countCountries());
        }
        return countryLogic.getCountries(page, maxRecords);
    }

    @GET
    @Path("{id}")
    public CountryDTO getCountry(@PathParam("id") Long id) {
        return countryLogic.getCountry(id);
    }

    @PUT
    @Path("{id}")
    public CountryDTO updateCountry(@PathParam("id") Long id, CountryDTO dto) {
        dto.setId(id);
        return countryLogic.updateCountry(dto);
    }

    @GET
    @Path("mostPopulated")
    public CountryDTO getMostPopulated() {
        return countryLogic.getMostPopulated();
    }

    @GET
    @Path("leastPopulated")
    public CountryDTO getLeastPopulated() {
        return countryLogic.getLeastPopulated();
    }
    
    @GET
    @Path("{id}/sports")
    public List<SportDTO> getCountrySports(@PathParam("id") Long id) {
        return countryLogic.getCountrySports(id);
    }
    
    @GET
    @Path("{id}/ownedSports")
    public List<SportDTO> getCountryOwnedSports(@PathParam("id") Long id) {
        return countryLogic.getCountryOwnedSports(id);
    }
}
