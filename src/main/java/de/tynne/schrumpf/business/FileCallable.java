/*
 * Copyright (C) 2014 Stephan Fuhrmann <stephan@tynne.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.tynne.schrumpf.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class FileCallable implements Callable<FileCallable> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(FileCallable.class);
    
    /** The input file to operate on. */
    private final File file;
    
    /** The settings for resizing. */
    private final ResizeBean resizeBean;
    
    /** The settings for the new image format. */
    private final FormatBean formatBean;
   
    /** The settings for the target name. */
    private final NamingBean namingBean;

    public FileCallable(File file, ResizeBean resizeBean, FormatBean formatBean, NamingBean namingBean) {
        this.file = Objects.requireNonNull(file);
        this.resizeBean = Objects.requireNonNull(resizeBean);
        this.formatBean = Objects.requireNonNull(formatBean);
        this.namingBean = Objects.requireNonNull(namingBean);
    }

    public File getFile() {
        return file;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.file);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileCallable other = (FileCallable) obj;
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }

    @Override
    public FileCallable call() throws Exception {
        
        ImageReader imageReader = null;
        ImageWriter imageWriter = null;
        
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(file)) {
            Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(imageInputStream);

            if (readerIterator.hasNext()) {
                imageReader = readerIterator.next();
                imageReader.setInput(imageInputStream);
                int numImages = imageReader.getNumImages(true);
                LOGGER.debug("Found image reader for format {}, number of images {}", imageReader.getFormatName(), numImages);
                BufferedImage image = imageReader.read(0);
                BufferedImage scaled = resizeBean.scaleImage(image);

                Iterator<ImageWriter> writerIterator = formatBean.getWriterFor(imageReader);
                if (writerIterator.hasNext()) {
                    imageWriter = writerIterator.next();

                    File target = namingBean.getTargetName(imageWriter, file);
                    
                    if (target.exists() && ! namingBean.isOverwrite()) {                        
                        throw new SkippedException("Skipping already existing image file " + target.getAbsolutePath());
                    }
                    
                    try (ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(target)) {
                        imageWriter.setOutput(imageOutputStream);
                        imageWriter.write(scaled);
                    }
                } else {
                    LOGGER.warn("Found no image writer for '{}'", file.getAbsolutePath());
                    throw new IOException("No image writer for " + file.getAbsolutePath());
                }                
            } else {
                LOGGER.warn("Found no image reader for '{}'", file.getAbsolutePath());
                throw new IOException("No image reader for " + file.getAbsolutePath());
            }
        }
        
        finally {
            if (imageReader != null) {
                imageReader.dispose();
            }
            if (imageWriter != null) {
                imageWriter.dispose();
            }
        }

        return this;
    }
}
