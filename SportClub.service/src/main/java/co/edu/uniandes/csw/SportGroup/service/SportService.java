/* ========================================================================
 * Copyright 2014 SportGroup
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 SportGroup
  
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
  
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
  
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 * ========================================================================
  
  
 Source generated by CrudMaker version 1.0.0.201411201032*/
package co.edu.uniandes.csw.SportGroup.service;

import co.edu.uniandes.csw.SportGroup.sport.logic.api.ISportLogic;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportDTO;
import co.edu.uniandes.csw.SportGroup.sport.logic.dto.SportPageDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/sports")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SportService {

    @Inject
    protected ISportLogic sportLogicService;

    @POST
    public SportDTO createSport(SportDTO sport) {
        return sportLogicService.createSport(sport);
    }

    @DELETE
    @Path("{id}")
    public void deleteSport(@PathParam("id") Long id) {
        sportLogicService.deleteSport(id);
    }

    @GET
    public SportPageDTO getSports(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        return sportLogicService.getSports(page, maxRecords);
    }

    @GET
    @Path("{id}")
    public SportDTO getSport(@PathParam("id") Long id) {
        return sportLogicService.getSport(id);
    }

    @PUT
    @Path("{id}")
    public SportDTO updateSport(@PathParam("id") Long id, SportDTO sport) {
        return sportLogicService.updateSport(sport);
    }

}
