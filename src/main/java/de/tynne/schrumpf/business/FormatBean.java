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

import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;

/**
 * The logic for outputting a certain image format.
 * @see #getWriterFor(javax.imageio.ImageReader) 
 * @author Stephan Fuhrmann <stephan@tynne.de>
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
