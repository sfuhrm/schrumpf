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

import de.odysseus.el.misc.TypeConverter;
import de.odysseus.el.misc.TypeConverterImpl;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Maps bean values to prefs and vice versa.
 * This class replaces a lot of data field copying to a descriptive properties file.
 * Uses properties objects for control.
 * The properties keys are EL expressions referring to {@link #ROOT_NAME} which
 * is the bean object passed in the arguments of the map methods.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class BeanPrefsMapper {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(BeanPrefsMapper.class);

    public final static String ROOT_NAME = "root";
    
    private BeanPrefsMapper() {
        // no instance allowed
    }
    
    private static Preferences getPrefsFor(Object bean) {
        Preferences prefs = Preferences.userNodeForPackage(bean.getClass());
        return prefs;
    }
    
    /** Writes the defaults into the given bean.
     * @param bean the bean to write the defaults to.
     * @param fields the descriptions of the fields to operate on.
     * The keys are the EL expressions relative to the given
     * <code>bean</code> with the {@link #ROOT_NAME}, the
     * values are the defaults.
     */
    public static void mapDefaultsToBean(Object bean, Properties fields) {
        Objects.requireNonNull(bean);
        
        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();
        de.odysseus.el.util.SimpleContext context = new de.odysseus.el.util.SimpleContext();
        TypeConverter converter = new TypeConverterImpl();
        
        context.setVariable(ROOT_NAME, factory.createValueExpression(bean, bean.getClass()));
        
        for (Map.Entry<Object,Object> entry : fields.entrySet()) {
            String key = (String) entry.getKey();
            String defaultValue = (String) entry.getValue();
            
            MDC.put("key", key);
            
            LOGGER.debug("defaultValue = '{}'", defaultValue);
            
            ValueExpression exp = factory.createValueExpression(context, key, Object.class);

            Object oldValue = exp.getValue(context);
            
            ValueExpression typedExp = factory.createValueExpression(context, key, oldValue.getClass());
                        
            Object convertedPrefsValue = converter.convert(defaultValue, oldValue.getClass());
            LOGGER.debug("Mapping '{}' to '{}', old value is '{}'. Type is {}", key, defaultValue, oldValue, oldValue.getClass().getName());
            
            typedExp.setValue(context, convertedPrefsValue);
        }
    }
    
    /**
     * Gets the prefereces for the given bean class and writes them to the bean.
     * Falls back to the fields passed in.
     * 
     * @param bean the bean to write to.
     * @param fields the descriptions of the fields to operate on.
     * The keys are the EL expressions relative to the given
     * <code>bean</code> with the {@link #ROOT_NAME}, the
     * values are the defaults.
     */
    public static void mapPrefsToBean(Object bean, Properties fields) {
        Objects.requireNonNull(bean);
        
        LOGGER.debug("before prefs");
        Preferences prefs = getPrefsFor(bean);
        try {
            prefs.sync();
        } catch (BackingStoreException ex) {
            LOGGER.error("Error sync()ing.", ex);
        }
        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();
        de.odysseus.el.util.SimpleContext context = new de.odysseus.el.util.SimpleContext();
        TypeConverter converter = new TypeConverterImpl();
        
        context.setVariable(ROOT_NAME, factory.createValueExpression(bean, bean.getClass()));
        
        for (Map.Entry<Object,Object> entry : fields.entrySet()) {
            String key = (String) entry.getKey();
            String defaultValue = (String) entry.getValue();
            
            MDC.put("key", key);
            
            // the value read from the prefs
            String prefsValue = prefs.get(key, defaultValue);
            
            LOGGER.debug("defaultValue = '{}', prefsValue = '{}'", defaultValue, prefsValue);
            
            ValueExpression exp = factory.createValueExpression(context, key, Object.class);

            Object oldValue = exp.getValue(context);
            
            ValueExpression typedExp = factory.createValueExpression(context, key, oldValue.getClass());
                        
            Object convertedPrefsValue = converter.convert(prefsValue, oldValue.getClass());
            LOGGER.debug("Mapping '{}' to '{}', old value is '{}'. Type is {}", key, prefsValue, oldValue, oldValue.getClass().getName());
            
            typedExp.setValue(context, convertedPrefsValue);
        }
    }
    
    /**
     * Writes the prefereces to the preferences backing storage from the
     * beans values.
     * Falls back to the fields passed in.
     * 
     * @param bean the bean to read from.
     * @param fields the descriptions of the fields to operate on.
     * The keys are the EL expressions relative to the given
     * <code>bean</code> with the {@link #ROOT_NAME}, the
     * values are the defaults.
     */
    public static void mapBeanToPrefs(Object bean, Properties fields) {
        Objects.requireNonNull(bean);
        
        LOGGER.debug("before prefs");
        Preferences prefs = getPrefsFor(bean);

        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();
        de.odysseus.el.util.SimpleContext context = new de.odysseus.el.util.SimpleContext();
        context.setVariable(ROOT_NAME, factory.createValueExpression(bean, bean.getClass()));
        
        for (Map.Entry<Object,Object> entry : fields.entrySet()) {
            String key = (String) entry.getKey();
            
            MDC.put("key", key);
                                   
            ValueExpression exp = factory.createValueExpression(context, key, String.class);

            String value = (String) exp.getValue(context);
            
            LOGGER.debug("Mapping '{}' to '{}'", key, value);
            prefs.put(key, value);
        }
        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            LOGGER.error("Error sync()ing.", ex);
        }
    }
}
