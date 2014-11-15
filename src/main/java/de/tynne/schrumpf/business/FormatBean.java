/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;

/**
 *
 * @author fury
 */
public class FormatBean {
    private boolean change;
    private String fileFormatName;

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public String getFileFormatName() {
        return fileFormatName;
    }

    public void setFileFormatName(String fileFormatName) {
        this.fileFormatName = fileFormatName;
    }
    
    public Iterator<ImageWriter> getWriterFor(ImageReader imageReader) throws IOException {
        Iterator<ImageWriter> result;
        if (change) {
            result = ImageIO.getImageWritersByFormatName(fileFormatName);
        } else {
            result = ImageIO.getImageWritersByFormatName(imageReader.getFormatName());
        }
        return result;
    }
}
