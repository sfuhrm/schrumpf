/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
