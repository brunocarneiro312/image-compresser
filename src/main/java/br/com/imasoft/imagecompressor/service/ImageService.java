package br.com.imasoft.imagecompressor.service;

import br.com.imasoft.imagecompressor.entity.Image;

import java.util.List;

public interface ImageService {

    /**
     * Comprime uma imagem
     * @param image
     * @return image compressed
     * @throws Exception
     */
    Image compress(Image image) throws Exception;

    /**
     * Comprime uma lista de imagens
     * @param images
     * @return list of image compressed
     */
    List<Image> compress(List<Image> images) throws Exception;
}
