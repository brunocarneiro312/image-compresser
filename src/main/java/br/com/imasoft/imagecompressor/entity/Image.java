package br.com.imasoft.imagecompressor.entity;

import lombok.Data;
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

    private String filename;

    private Long filesize;

    private byte[] bytearray;

}
