package co.edu.uniandes.csw.sportgroup.service;

import co.edu.uniandes.csw.sportclub.api.ISportLogic;
import co.edu.uniandes.csw.sportclub.dtos.SportDTO;
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

@Path("/sports")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SportService {

    @Inject
    private ISportLogic sportLogic;
    @Context
    private HttpServletResponse response;
    @QueryParam("page")
    private Integer page;
    @QueryParam("maxRecords")
    private Integer maxRecords;
    @QueryParam("name")
    private String name;

    @POST
    @StatusCreated
    public SportDTO createSport(SportDTO sport) {
        return sportLogic.createSport(sport);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteSport(@PathParam("id") Long id) {
        sportLogic.deleteSport(id);
    }

    @GET
    public List<SportDTO> getSports() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", sportLogic.countSports());
        }
        if (name != null) {
            return sportLogic.searchByName(name);
        }
        return sportLogic.getSports(page, maxRecords);
    }

    @GET
    @Path("{id: \\d+}")
    public SportDTO getSport(@PathParam("id") Long id) {
        return sportLogic.getSport(id);
    }

    @PUT
    @Path("{id: \\d+}")
    public SportDTO updateSport(@PathParam("id") Long id, SportDTO dto) {
        dto.setId(id);
        return sportLogic.updateSport(dto);
    }
}
