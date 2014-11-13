/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.prefs;

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
 * Uses properties objects for control.
 * The properties keys are EL expressions referring to {@link #ROOT_NAME} which
 * is the bean object passed in the arguments of the map methods.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class BeanPrefsMapper {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(BeanPrefsMapper.class);

    public final static String ROOT_NAME = "root";
    
    private BeanPrefsMapper() {
    }
        
    public static void mapPrefsToBean(Object bean, Properties fields) {
        Objects.requireNonNull(bean);
        
        LOGGER.debug("before prefs");
        Preferences prefs = Preferences.userNodeForPackage(bean.getClass());
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
            
            LOGGER.debug("defaultValue = {}, prefsValue = {}", defaultValue, prefsValue);
            
            ValueExpression exp = factory.createValueExpression(context, key, Object.class);

            Object oldValue = exp.getValue(context);
            
            ValueExpression typedExp = factory.createValueExpression(context, key, oldValue.getClass());
                        
            Object convertedPrefsValue = converter.convert(prefsValue, oldValue.getClass());
            LOGGER.debug("Mapping {} to {}, old value is {}", key, prefsValue, oldValue);
            
            typedExp.setValue(context, convertedPrefsValue);
        }
    }
    
    public static void mapBeanToPrefs(Object bean, Properties fields) {
        Objects.requireNonNull(bean);
        
        LOGGER.debug("before prefs");
        Preferences prefs = Preferences.userNodeForPackage(bean.getClass());
        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();
        de.odysseus.el.util.SimpleContext context = new de.odysseus.el.util.SimpleContext();
        context.setVariable(ROOT_NAME, factory.createValueExpression(bean, bean.getClass()));
        
        for (Map.Entry<Object,Object> entry : fields.entrySet()) {
            String key = (String) entry.getKey();
            
            MDC.put("key", key);
                                   
            ValueExpression exp = factory.createValueExpression(context, key, String.class);

            String value = (String) exp.getValue(context);
            
            LOGGER.debug("Mapping {} to {}", key, value);
            prefs.put(key, value);
        }
        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            LOGGER.error("Error sync()ing.", ex);
        }
    }
}
