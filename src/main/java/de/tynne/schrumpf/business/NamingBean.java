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

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The logic for finding an output file name and directory
 * for an image file.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class NamingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(NamingBean.class);

    private boolean change;
    private boolean hasDirectory;
    private String prefix;
    private boolean hasPrefix;
    private String suffix;
    private boolean hasSuffix;
    private String directory;
    private boolean overwrite;
    
    public void setChange(boolean selected) {
        this.change = selected;
    }

    public void setHasDirectory(boolean selected) {
        this.hasDirectory = selected;
    }

    public void setHasPrefix(boolean selected) {
        this.hasPrefix = selected;
    }

    public void setPrefix(String text) {
        this.prefix = text;
    }

    public void setHasSuffix(boolean selected) {
        this.hasSuffix = selected;
    }

    public void setSuffix(String text) {
        this.suffix = text;                
    }

    public void setDirectory(String text) {
        this.directory = text;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }
    
    public File getTargetName(ImageWriter imageWriter, File inFile) {
        
        File parent = inFile.getParentFile();
        if (hasDirectory) {
            parent = new File(directory);
        }
        LOGGER.debug("Target dir = {}", parent.getAbsolutePath());
        
        File newParent = hasDirectory ? new File(directory) : parent;
        String newName = newName(imageWriter, inFile.getName());
        
        File result = new File(newParent, newName);
        
        LOGGER.debug("Input: {}, Output: {}", inFile.getAbsolutePath(), result.getAbsolutePath());
        
        return result;
    }
    
    public static String[] parseNameAndSuffix(String inName) {
        Pattern typeSuffixPattern = Pattern.compile("(.*)\\.([^.]+)");

        Matcher matcher = typeSuffixPattern.matcher(inName);
        String nameBase;
        String nameSuffix;

        if (matcher.matches()) {
            // has suffix
            nameBase = matcher.group(1);
            nameSuffix = matcher.group(2);
        } else {
            nameBase = inName;
            nameSuffix = "";
        }
        return new String[] {nameBase, nameSuffix};
    }
    
    private String newName(ImageWriter imageWriter, String inName) {
        
        String nameAndSuffix[] = parseNameAndSuffix(inName);
        String nameBase = nameAndSuffix[0];
        String nameSuffix = nameAndSuffix[1];
                
        LOGGER.debug("Name: {}, Base: {}, Suffix: {}", inName, nameBase, nameSuffix);
        
        StringBuilder newBase = new StringBuilder(nameBase);
        String newSuffix = nameSuffix;
        
        if (change && hasPrefix) {
            newBase.insert(0, prefix);
        }
        if (change && hasSuffix) {
            newBase.append(suffix);
        }
        
        String suffixes[] = imageWriter.getOriginatingProvider().getFileSuffixes();
        if (suffixes.length > 0) {
            newSuffix = suffixes[0];
        }
        String newName = newBase + "." + newSuffix;
        
        LOGGER.debug("New Name: {}, Base: {}, Suffix: {}", newName, newBase, newSuffix);
        
        return newName;
    }
}
