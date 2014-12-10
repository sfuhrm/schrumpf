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
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author fury
 */
public class ResizeBeanTest {

    BufferedImage test;
    
    @Before
    public void init() {
        test = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
    }
    
    @Test
    public void testNoChange() {
        ResizeBean resizeBean = new ResizeBean();
        resizeBean.setChange(false);
        resizeBean.setKeepAspect(true);
        resizeBean.setPreferredWidth(640);
        resizeBean.setPreferredHeight(480);
        
        BufferedImage out = resizeBean.scaleImage(test);
        
        assertEquals(1024, out.getWidth());
        assertEquals(768, out.getHeight());
        assertEquals(BufferedImage.TYPE_INT_ARGB, out.getType());
    }
    
    @Test
    public void testScaleKeepAspect() {
        ResizeBean resizeBean = new ResizeBean();
        resizeBean.setChange(true);
        resizeBean.setKeepAspect(true);
        resizeBean.setPreferredWidth(640);
        resizeBean.setPreferredHeight(768);
        
        BufferedImage out = resizeBean.scaleImage(test);
        
        assertEquals(640, out.getWidth());
        assertEquals((int)(768 * ((double)640 / ((double)1024))), out.getHeight());
        assertEquals(BufferedImage.TYPE_INT_ARGB, out.getType());
    }
    
    @Test
    public void testScaleNoKeepAspect() {
        ResizeBean resizeBean = new ResizeBean();
        resizeBean.setChange(true);
        resizeBean.setKeepAspect(false);
        resizeBean.setPreferredWidth(640);
        resizeBean.setPreferredHeight(768);
        
        BufferedImage out = resizeBean.scaleImage(test);
        
        assertEquals(640, out.getWidth());
        assertEquals(768, out.getHeight());
        assertEquals(BufferedImage.TYPE_INT_ARGB, out.getType());
    }
    
    @Test
    public void testScaleKeepModeGray() {
        BufferedImage myTest = new BufferedImage(640, 480, BufferedImage.TYPE_BYTE_GRAY);
        ResizeBean resizeBean = new ResizeBean();
        resizeBean.setChange(true);
        resizeBean.setKeepAspect(false);
        resizeBean.setPreferredWidth(640);
        resizeBean.setPreferredHeight(768);
        
        BufferedImage out = resizeBean.scaleImage(myTest);
        
        assertEquals(640, out.getWidth());
        assertEquals(768, out.getHeight());
        assertEquals(BufferedImage.TYPE_BYTE_GRAY, out.getType());
    }
    
}
