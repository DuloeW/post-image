package org.acme.controller;

import java.io.IOException;

import org.acme.entity.ImageEntity;
import org.acme.model.body.ImageBody;
import org.acme.service.ImageService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/image")
public class ImageController {

    @Inject
    ImageService imageService;

    @GET
    @Path("/get/{id}")
    public Response geEntityById(@PathParam("id") Long id) {
        return imageService.getImageById(id);
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllImage() {
        return imageService.getAllImage();
    }

    @POST
    @Path("/uploud")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Uni<Response> uploudFile(@MultipartForm ImageBody body) throws IOException {
        return imageService.uploudFile(body);
    }
}
