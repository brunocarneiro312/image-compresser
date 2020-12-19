package br.com.imasoft.imagecompressor.service.impl;

import br.com.imasoft.imagecompressor.entity.Image;
import br.com.imasoft.imagecompressor.repository.ImageRepository;
import br.com.imasoft.imagecompressor.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
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

        try {

            File unconpressedFile = new File("/tmp/" + image.getFilename());

            ByteArrayInputStream bis = new ByteArrayInputStream(image.getBytearray());
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, "jpg", unconpressedFile);

            BufferedImage img = ImageIO.read(unconpressedFile);

            File output = new File(unconpressedFile.getAbsolutePath());
            OutputStream out = new FileOutputStream(output);
            long outputSize = Files.size(unconpressedFile.toPath());

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.05f);
            }

            writer.write(null, new IIOImage(img, null, null), param);

            ios.close();
            out.close();
            writer.dispose();

            File compressedFile = new File("/tmp/" + image.getFilename());
            FileInputStream fis = new FileInputStream(compressedFile);
            Image imageCompressed = Image.of(image.getFilename(), fis.readAllBytes());
            imageCompressed.setFilesize(Files.size(compressedFile.toPath()));

            System.out.println("antes .................. :" + outputSize + "KB");
            System.out.println("depois .................. :" + Files.size(compressedFile.toPath()) + "KB");

            fis.close();

            return imageCompressed;
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<Image> compress(List<Image> images) throws Exception {
        return null;
    }
}
