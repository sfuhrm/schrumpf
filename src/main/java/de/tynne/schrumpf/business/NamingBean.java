/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

/**
 *
 * @author fury
 */
public class NamingBean {

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
    
}
