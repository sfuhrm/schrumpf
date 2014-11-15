/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.ui;

/**
 * Some video standard graphics formats.
 * @see http://de.wikipedia.org/wiki/Grafikstandard#mediaviewer/File:Vector_Video_Standards2.svg
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public enum VideoStandardFormat {
    CGA(320,200),
    VGA(640,480),
    XGA(1024,768),
    SXGA(1280,1024),
    QSXGA(2560,2048);
    
    private final int width;
    private final int height;
    
    private VideoStandardFormat(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }

    @Override
    public String toString() {
        return name()+" "+width+"x"+height;
    }
}
