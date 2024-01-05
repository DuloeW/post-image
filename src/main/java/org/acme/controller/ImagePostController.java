package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.acme.entity.ImageEntity;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Path("/upload")
public class ImagePostController {

  @Inject
  EntityManager entityManager;

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.TEXT_PLAIN)
  @Transactional
  public Response uploadFile(@MultipartForm FormData formData) throws IOException {
    ImageEntity imageEntity = new ImageEntity();
    imageEntity.setFileName(formData.fileName);
    imageEntity.setFile(formData.file.readAllBytes());
    imageEntity.setId(generateId());


    entityManager.persist(imageEntity);
    return Response
      .ok("File uploaded and saved in MySQL successfully!")
      .build();
  }

  public static class FormData {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
  }

    private Long generateId() {
    UUID uuid = UUID.randomUUID();
    return uuid.getMostSignificantBits();
  }
}
