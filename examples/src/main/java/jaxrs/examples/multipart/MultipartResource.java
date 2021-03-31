/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jaxrs.examples.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Part;

@Path("/multipart")
public class MultipartResource {

    private static final String PDF_ROOT_DIR = System.getProperty("pdf.root.dir", "/myPDFs");

    @GET
    public List<Part> getAllPdfFilesInDirectory(@QueryParam("dirName") String dirName) throws IOException {
        File dir = getDirectoryIfExists(dirName);
        List<Part> parts = new ArrayList<>();
        for (File f : dir.listFiles()) {
            parts.add(Part.newBuilder(f.getName()).content(f.getName(), new FileInputStream(f))
                                                  .mediaType("application/pdf")
                                                  .build());
        }
        return parts;
    }

    @POST
    public Response postNewPdfFiles(@QueryParam("dirName") String dirName, List<Part> parts) throws IOException {
        File dir = getDirectoryIfExists(dirName);
        for (Part p : parts) {
            File f = new File(dir, p.getFileName().orElseThrow(BadRequestException::new));
            if (f.exists()) {
                throw new WebApplicationException(409); // 409 CONFLICT
            }
            try (InputStream content = p.getContent()) {
                Files.copy(content, f.toPath());
            }
        }
        return Response.ok().build();
    }

    private File getDirectoryIfExists(String dirName) {
        File dir = new File(PDF_ROOT_DIR, dirName);
        if (!dir.exists()) {
            throw new NotFoundException("dirName, " + dirName + ", does not exist");
        }
        return dir;
    }
}
