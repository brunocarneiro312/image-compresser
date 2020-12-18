package br.com.imasoft.imagecompressor.repository;

import br.com.imasoft.imagecompressor.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {

}
