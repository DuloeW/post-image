package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.entity.ImageEntity;
import org.acme.model.body.ImageBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
public class ImageService {

  public Response getAllImage() {
    var images = ImageEntity
      .findAllImage()
      .stream()
      .collect(Collectors.toList());
    return Response.ok(images).build();
  }

  public Response uploudFile(ImageBody body) throws IOException {
    Objects.requireNonNull(body);
    ImageEntity image = body.mapToImage();
    image.persist();
    return Response
      .ok("File uploaded and saved in MySQL successfully!")
      .build();
  }
}
