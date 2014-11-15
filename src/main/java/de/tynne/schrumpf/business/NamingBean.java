/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fury
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
    
    private String newName(ImageWriter imageWriter, String inName) {
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
