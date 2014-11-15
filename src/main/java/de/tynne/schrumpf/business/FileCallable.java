/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resizes a single image.
 * @author fury
 */
public class FileCallable implements Callable<Object> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(FileCallable.class);
    private final File file;
    private final ResizeBean resizeBean;
    private final FormatBean formatBean;
    private final NamingBean namingBean;

    public FileCallable(File file, ResizeBean resizeBean, FormatBean formatBean, NamingBean namingBean) {
        this.file = Objects.requireNonNull(file);
        this.resizeBean = Objects.requireNonNull(resizeBean);
        this.formatBean = Objects.requireNonNull(formatBean);
        this.namingBean = Objects.requireNonNull(namingBean);
    }

    @Override
    public Object call() throws Exception {
        
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(imageInputStream);
        
        ImageReader imageReader;
        if (readerIterator.hasNext()) {
            imageReader = readerIterator.next();
            imageReader.setInput(imageInputStream);
            int numImages = imageReader.getNumImages(true);
            LOGGER.debug("Found image reader for format {}, number of images {}", imageReader.getFormatName(), numImages);
            BufferedImage image = imageReader.read(0);            
            BufferedImage scaled = resizeBean.scaleImage(image);

            Iterator<ImageWriter> writerIterator = formatBean.getWriterFor(imageReader);
            if (writerIterator.hasNext()) {
                ImageWriter imageWriter = writerIterator.next();
                
                File target = namingBean.getTargetName(imageWriter, file);
                
                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(target);
                imageWriter.setOutput(imageOutputStream);
                imageWriter.write(scaled);
            } else {
                LOGGER.warn("Found no image writer for '{}'", file.getAbsolutePath());
            }
        } else {
            LOGGER.warn("Found no image reader for '{}'", file.getAbsolutePath());
        }
        
        // TODO dispose and close stuff
        
        return null;
    }
}
