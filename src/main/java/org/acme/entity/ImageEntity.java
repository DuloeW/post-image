package org.acme.entity;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "images")
@Setter
public class ImageEntity extends PanacheEntityBase {

    @Id
    public Long id;

    @Column(name = "file_name")
    public String fileName;

    @Column(name = "file_content", columnDefinition = "blob")
    public byte[] file;

    public static List<ImageEntity> findAllImage() {
        return ImageEntity.listAll();
    }
}
