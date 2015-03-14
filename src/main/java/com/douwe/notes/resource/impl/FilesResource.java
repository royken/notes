package com.douwe.notes.resource.impl;

import com.douwe.notes.resource.IFilesResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("files")
public class FilesResource implements IFilesResource {

    private static final String FILE_PATH = "/home/royken/Programmes/notes/files/";

    @Override
    public Response getFile(String file) {
        File filed = new File(FILE_PATH + file);
        ResponseBuilder response = Response.ok((Object) filed);
        response.header("Content-Disposition",
                "attachment; filename="+file);
        return response.build();

    }

    @Override
    public String uploadFile(String file) throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
