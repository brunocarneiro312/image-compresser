package br.com.imasoft.imagecompressor.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@RequiredArgsConstructor(staticName = "of")
@Document
public class Image implements Serializable {

    @Id
    private String id;

    @NonNull
    private String filename;

    private Long filesize;

    @NonNull
    private byte[] bytearray;

}
