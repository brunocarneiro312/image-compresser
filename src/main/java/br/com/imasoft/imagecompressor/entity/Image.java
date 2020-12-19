package br.com.imasoft.imagecompressor.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor(staticName = "of")
@Document(collection = "compressed")
public class Image implements Serializable {

    @Id
    private String id;

    @NonNull
    private String filename;

    private Long filesize;

    @NonNull
    private Binary bytearray;

    @Field("compressed_at")
    private LocalDateTime compressedAt;

}
