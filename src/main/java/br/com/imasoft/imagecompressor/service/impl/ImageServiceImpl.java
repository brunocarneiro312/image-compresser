package br.com.imasoft.imagecompressor.service.impl;

import br.com.imasoft.imagecompressor.entity.Image;
import br.com.imasoft.imagecompressor.repository.ImageRepository;
import br.com.imasoft.imagecompressor.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

            File imageFile = new File("src/main/resources/assets/img/" + image.getFilename());

            FileUtils.writeByteArrayToFile(imageFile,
                    image.getBytearray().getData());

            BufferedImage bufferedImage = ImageIO.read(new FileImageOutputStream(imageFile));

            File output = new File(imageFile.getAbsolutePath());
            OutputStream imageFileOutputStream = new FileOutputStream(output);


            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(imageFileOutputStream);
            writer.setOutput(imageOutputStream);

            ImageWriteParam param = writer.getDefaultWriteParam();
            applyCompression(param);

            writer.write(null, new IIOImage(bufferedImage, null, null), param);

            imageOutputStream.close();
            imageFileOutputStream.close();
            writer.dispose();

            File compressedFile = new File("src/main/resources/assets/img/" + image.getFilename());
            FileInputStream fis = new FileInputStream(compressedFile);
            Image imageCompressed = Image.of(image.getFilename(), new Binary(BsonBinarySubType.BINARY, fis.readAllBytes()));
            imageCompressed.setFilesize(Files.size(compressedFile.toPath()));
            imageCompressed.setCompressedAt(LocalDateTime.now());

            this.imageRepository.save(imageCompressed);

            fis.close();
            Files.deleteIfExists(compressedFile.toPath());

            return imageCompressed;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<Image> compress(List<Image> images) throws Exception {
        return null;
    }

    private void applyCompression(ImageWriteParam param) throws Exception {
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.05f);
        }
    }

}
