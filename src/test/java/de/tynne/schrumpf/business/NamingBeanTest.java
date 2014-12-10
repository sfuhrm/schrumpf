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
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author fury
 */
public class NamingBeanTest {
    
    File test1;
    ImageWriter jpg;
    ImageWriter png;
    
    final static String TEST_BASE = "abcdef000..";
    final static String TEST_SUFFIX = "jpg";
    
    @Before
    public void init() {
        test1 = new File("/tmp/"+TEST_BASE+"."+TEST_SUFFIX);
        jpg = ImageIO.getImageWritersBySuffix("jpg").next();
        png = ImageIO.getImageWritersBySuffix("png").next();
    }
    
    @Test
    public void testNoChange() {
        NamingBean namingBean = new NamingBean();
        namingBean.setChange(false);
        namingBean.setDirectory("/foo");
        namingBean.setHasDirectory(false);
        namingBean.setHasPrefix(true);
        namingBean.setPrefix("x");
        namingBean.setHasSuffix(true);
        namingBean.setSuffix("y");
        
        File target = namingBean.getTargetName(jpg, test1);
        
        assertEquals(test1.getParent(), target.getParent());
        assertEquals(TEST_BASE+"."+TEST_SUFFIX, target.getName());
    }
    
    @Test
    public void testDirectoryChange() {
        NamingBean namingBean = new NamingBean();
        namingBean.setChange(true);
        namingBean.setDirectory("/foo");
        namingBean.setHasDirectory(true);
        namingBean.setHasPrefix(false);
        namingBean.setPrefix(null);
        namingBean.setHasSuffix(false);
        namingBean.setSuffix(null);
        
        File target = namingBean.getTargetName(jpg, test1);
        
        assertEquals("/foo", target.getParent());
        assertEquals(TEST_BASE+"."+TEST_SUFFIX, target.getName());
    }
    
    @Test
    public void testPrefixChange() {
        NamingBean namingBean = new NamingBean();
        namingBean.setChange(true);
        namingBean.setDirectory(null);
        namingBean.setHasDirectory(false);
        namingBean.setHasPrefix(true);
        namingBean.setPrefix("XXX_");
        namingBean.setHasSuffix(false);
        namingBean.setSuffix(null);
        
        File target = namingBean.getTargetName(jpg, test1);
        
        assertEquals(test1.getParent(), target.getParent());
        assertEquals("XXX_"+TEST_BASE+"."+TEST_SUFFIX, target.getName());
    }    
    
    @Test
    public void testSuffixChange() {
        NamingBean namingBean = new NamingBean();
        namingBean.setChange(true);
        namingBean.setDirectory(null);
        namingBean.setHasDirectory(false);
        namingBean.setHasPrefix(false);
        namingBean.setPrefix(null);
        namingBean.setHasSuffix(true);
        namingBean.setSuffix("XYZ");
        
        File target = namingBean.getTargetName(jpg, test1);
        
        assertEquals(test1.getParent(), target.getParent());
        assertEquals(TEST_BASE+"XYZ"+"."+TEST_SUFFIX, target.getName());
    }
    
    @Test
    public void testFormatChange() {
        NamingBean namingBean = new NamingBean();
        namingBean.setChange(true);
        namingBean.setDirectory(null);
        namingBean.setHasDirectory(false);
        namingBean.setHasPrefix(false);
        namingBean.setPrefix(null);
        namingBean.setHasSuffix(false);
        
        File target = namingBean.getTargetName(png, test1);
        
        assertEquals(test1.getParent(), target.getParent());
        assertEquals(TEST_BASE+".png", target.getName());
    }    
}
