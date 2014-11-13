/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.prefs;

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
