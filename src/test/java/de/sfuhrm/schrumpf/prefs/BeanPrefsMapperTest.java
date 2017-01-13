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
package de.sfuhrm.schrumpf.prefs;

import de.sfuhrm.schrumpf.prefs.BeanPrefsMapper;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class BeanPrefsMapperTest {
    
    
    static class BeanA {

        private String a;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    };
    
    @Before
    public void init() throws BackingStoreException {
        Preferences.userNodeForPackage(BeanA.class).clear();
    }
    
    @Test
    public void mapPrefsToBean() {
               
        BeanA beanA = new BeanA();
        beanA.a = "foo";
        
        Properties props = new Properties();
        props.setProperty("${root.a}", "bar");
        
        BeanPrefsMapper.mapPrefsToBean(beanA, props);
        
        // expecting default value
        assertEquals("bar", beanA.a);
    }
    
    
    @Test
    public void mapBeanToPrefs() {
               
        BeanA beanA = new BeanA();
        beanA.a = "foo";
        
        Properties props = new Properties();
        props.setProperty("${root.a}", "bar");
        
        BeanPrefsMapper.mapBeanToPrefs(beanA, props);
        
        // expecting bean value
        assertEquals("foo", Preferences.userNodeForPackage(BeanA.class).get("${root.a}", "xxx"));
    }
}
