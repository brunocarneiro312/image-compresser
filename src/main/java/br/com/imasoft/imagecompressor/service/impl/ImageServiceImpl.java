package br.com.imasoft.imagecompressor.service.impl;

import br.com.imasoft.imagecompressor.entity.Image;
import br.com.imasoft.imagecompressor.repository.ImageRepository;
import br.com.imasoft.imagecompressor.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image compress(Image image) throws Exception {
        return null;
    }

    @Override
    public List<Image> compress(List<Image> images) throws Exception {
        return null;
    }
}
