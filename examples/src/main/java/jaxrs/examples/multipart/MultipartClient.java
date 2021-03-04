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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Part;

public class MultipartClient {

    public boolean sendPdfs(File dir) throws IOException {
        List<Part> parts = new ArrayList<>();
        for (File f : dir.listFiles()) {
            parts.add(Part.newBuilder(f.getName()).entityStream(f.getName(), new FileInputStream(f))
                                                  .mediaType("application/pdf")
                                                  .build());
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9080/multipart?dirName=abc");
        Entity<List<Part>> entity = Entity.entity(parts, MediaType.MULTIPART_FORM_DATA);
        Response response = target.request().post(entity);
        return response.getStatus() == 200;
    }

    public List<File> retrievePdfs(String remoteDirName) throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9080/multipart").queryParam("dirName", remoteDirName);
        Response response = target.request(MediaType.MULTIPART_FORM_DATA).get();
        List<Part> parts = response.readEntity(new GenericType<List<Part>>(){});
        List<File> files = new ArrayList<>(parts.size());
        for (Part p : parts) {
            File f = new File(p.getFileName().orElse(p.getName()));
            f.createNewFile();
            Files.copy(p.getEntityStream(), f.toPath());
            files.add(f);
        }
        return files;
    }
}
