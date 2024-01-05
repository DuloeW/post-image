package org.acme.model.body;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.acme.entity.ImageEntity;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImageBody {

  @FormParam("file")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  public InputStream file;

  @FormParam("fileName")
  @PartType(MediaType.TEXT_PLAIN)
  public String fileName;

  public ImageEntity mapToImage() throws IOException {
    var image = new ImageEntity();
    image.id = generateId();
    image.fileName = fileName;
    image.file = file.readAllBytes();
    return image;
  }

  private Long generateId() {
    UUID uuid = UUID.randomUUID();
    return uuid.getMostSignificantBits();
  }
}
