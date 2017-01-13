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
package de.sfuhrm.schrumpf.ui;

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
