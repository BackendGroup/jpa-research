package co.edu.uniandes.csw.sportgroup.services;

import co.edu.uniandes.csw.sportgroup.providers.StatusCreated;
import co.edu.uniandes.csw.sportclub.api.ICountryLogic;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import java.util.List;

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

@Path("/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryService {

    @Inject
    private ICountryLogic countryLogic;
    @Context
    private HttpServletResponse response;
    @QueryParam("page")
    private Integer page;
    @QueryParam("maxRecords")
    private Integer maxRecords;

    @POST
    @StatusCreated
    public CountryDTO createCountry(CountryDTO sport) {
        return countryLogic.createCountry(sport);
    }

    @DELETE
    @Path("{id: \\d+}")
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
    @Path("{id: \\d+}")
    public CountryDTO getCountry(@PathParam("id") Long id) {
        return countryLogic.getCountry(id);
    }

    @PUT
    @Path("{id: \\d+}")
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

    @Path("/master")
    public CountryService getMasterService() {
        return this;
    }
}
