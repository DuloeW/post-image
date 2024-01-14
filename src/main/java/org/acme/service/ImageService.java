package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.acme.entity.ImageEntity;
import org.acme.model.body.ImageBody;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ImageService {

  public Response getImageById(Long id) {
    var image = ImageEntity
      .findImageById(id)
      .orElseThrow(() ->
        new WebApplicationException(
          Response.status(404).entity("Id Not Found").build()
        )
      );
    return Response.ok(image).build();
  }

  public Response getAllImage() {
    var images = ImageEntity
      .findAllImage()
      .stream()
      .collect(Collectors.toList());
    return Response.ok(images).build();
  }

  public Uni<Response> uploudFile(ImageBody body) {
        try {
            InputStream originalImageStream = body.file;
            BufferedImage originalImage = ImageIO.read(originalImageStream);

            // Atur ukuran yang diinginkan
            int scaledWidth = 200;
            int scaledHeight = 150;

            // Buat gambar baru dengan ukuran yang diinginkan
            BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g.dispose();

            ImageEntity imageEntity = body.mapToImage();

            // Konversi BufferedImage menjadi byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String imageString = Base64.getEncoder().encodeToString(imageBytes);
            imageEntity.setFile(imageString);

            // Simpan ke dalam basis data
            imageEntity.persist();

            return Uni.createFrom().item(Response.ok("{\"message\" : \"Gambar berhasil diresize dan disimpan ke dalam database\" }").build());
        } catch (IOException e) {
            return Uni.createFrom().failure(e);
        }
    }
}
