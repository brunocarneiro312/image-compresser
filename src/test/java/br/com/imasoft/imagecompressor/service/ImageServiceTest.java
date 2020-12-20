package br.com.imasoft.imagecompressor.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.jupiter.api.Assertions.fail;

class ImageServiceTest {

    @Test
    @DisplayName("Given image file, when compress, then return image file compressed")
    void compress() throws IOException {

        // given
        File file = new File("src/main/resources/assets/img/burguer.jpg");
        File cloned = new File("src/main/resources/assets/img/burguer_compressed_clone.jpg");

        FileInputStream fis = new FileInputStream(file);

        // when

        ByteArrayInputStream bis = new ByteArrayInputStream(fis.readAllBytes());
        BufferedImage bufferedImage = ImageIO.read(bis);
        ImageIO.write(bufferedImage, "jpg", cloned);

        BufferedImage image = ImageIO.read(cloned);

        File output = new File(cloned.getAbsolutePath());
        OutputStream out = new FileOutputStream(output);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.05f);
        }

        writer.write(null, new IIOImage(image, null, null), param);

        out.close();
        ios.close();
        writer.dispose();

        // then
    }

    @Test
    public void testReadImage() throws IOException {

        File file = new File("/home/brunocarneiro/Pictures/bruno.jpg");

        if (file.exists()) {
            assert (true);
        } else {
            fail("O arquivo não existe");
        }

        File fileCompressed = new File(file.getAbsolutePath()
                .replaceAll(file.getName(), "compressed_" + file.getName()));

        if (fileCompressed.createNewFile()) {
            FileUtils.copyFile(file, fileCompressed);
        }


        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        System.out.println(file.toPath());

        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(fileCompressed));
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(fileCompressed));
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(imageOutputStream);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.05f);
        }

        writer.write(null, new IIOImage(bufferedImage, null, null), param);

        writer.dispose();
    }
}