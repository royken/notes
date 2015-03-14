package com.douwe.notes.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/files")
public interface IFilesResource {

    @GET
    @Path("/download/{file}")
    @Produces("application/pdf")
    public Response getFile(@PathParam(value = "file") String file);

    @POST
    @Path("upload/{file}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@PathParam(value = "file") String file) throws FileNotFoundException, IOException;

}
