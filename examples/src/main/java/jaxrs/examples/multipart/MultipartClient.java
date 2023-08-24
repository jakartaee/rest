/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jaxrs.examples.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class MultipartClient {
    private static final Logger LOG = Logger.getLogger(MultipartClient.class.getName());

    public boolean sendPdfs(Path dir) throws IOException {
        List<EntityPart> parts = Files.list(dir).map(this::toPart).collect(Collectors.toList());
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9080/multipart?dirName=abc");
        Entity<List<EntityPart>> entity = Entity.entity(parts, MediaType.MULTIPART_FORM_DATA);
        Response response = target.request().post(entity);
        return response.getStatus() == 200;
    }

    private EntityPart toPart(Path file) {
        String filename = file.getFileName().toString();
        try {
            return EntityPart.withName(filename)
                             .content(filename, Files.newInputStream(file))
                             .mediaType("application/pdf")
                             .build();
        } catch (IOException ioex) {
            LOG.log(Level.WARNING, "Failed to process file {0}", file);
            return null;
        }
    }

    public List<Path> retrievePdfs(String remoteDirName) throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9080/multipart").queryParam("dirName", remoteDirName);
        Response response = target.request(MediaType.MULTIPART_FORM_DATA).get();
        List<EntityPart> parts = response.readEntity(new GenericType<List<EntityPart>>() { });
        return parts.stream().map(part -> {
            try (InputStream is = part.getContent()) {
                Path file = Files.createFile(Paths.get(part.getFileName().orElse(part.getName() + ".pdf")));
                Files.copy(is, file);
                return file;
            } catch (IOException ioex) {
                LOG.log(Level.WARNING, "Failed to process attachment part {0}", part);
                return null;
            }
        }).collect(Collectors.toList());
    }
}
