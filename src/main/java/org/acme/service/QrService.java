package org.acme.service;

import io.nayuki.fastqrcodegen.QrCode;
import io.nayuki.fastqrcodegen.QrCodeGeneratorDemo;
import jakarta.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import org.acme.entity.ImageEntity;
import org.json.JSONObject;

@ApplicationScoped
public class QrService {

  public List<ImageEntity> fetchImageEntity() {
    return ImageEntity.findAllImage().stream().collect(Collectors.toList());
  }

  public byte[] getAllQrImageBuffers() {
    List<ImageEntity> images = fetchImageEntity();

    try (
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ZipOutputStream zos = new ZipOutputStream(baos)
    ) {
      
      images.forEach(img -> {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nisn", "69420");
        jsonObject.put("name", img.fileName);
        jsonObject.put("kelas", "XII");
        jsonObject.put("jurusan", "RPL");
        jsonObject.put("image-id", String.valueOf(img.id));

        QrCode qr = QrCode.encodeText(jsonObject.toString(), QrCode.Ecc.MEDIUM);
        BufferedImage image = QrCodeGeneratorDemo.toImage(qr, 20, 4);
        byte[] imageBuf = convertToByteArray(image);

        try {
          addToZip(img.fileName + ".png", imageBuf, zos);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      // Finish writing to the zip file
      zos.finish();

      // Get the byte array from the ByteArrayOutputStream
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private static byte[] convertToByteArray(BufferedImage image) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      // Write the image data to the ByteArrayOutputStream
      ImageIO.write(image, "png", baos);

      // Get the byte array from the ByteArrayOutputStream
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private static void addToZip(
    String fileName,
    byte[] buffer,
    ZipOutputStream zos
  ) throws IOException {
    try (ByteArrayOutputStream bis = new ByteArrayOutputStream()) {
      ZipEntry zipEntry = new ZipEntry(fileName);
      zos.putNextEntry(zipEntry);

      // Write the buffer to the zip file
      zos.write(buffer, 0, buffer.length);

      zos.closeEntry();
    }
  }
}
