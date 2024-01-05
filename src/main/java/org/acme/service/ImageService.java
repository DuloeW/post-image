package org.acme.service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.acme.entity.ImageEntity;
import org.acme.model.body.ImageBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ImageService {

    public Response getAllImage() {
        var images = ImageEntity.findAllImage()
        .stream()
        .collect(Collectors.toList());
        return Response.ok(images).build();
    }

}
