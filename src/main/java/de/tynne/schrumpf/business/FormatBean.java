/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.io.File;

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
    
    public File getTargetNameFor(File in) {
        File result = in;
        if (change) {
            // TODO
        }
        return in;
    }
}
