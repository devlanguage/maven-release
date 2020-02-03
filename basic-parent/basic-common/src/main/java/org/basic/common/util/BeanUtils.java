package org.basic.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for manipulating the java bean object
 *
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static final Object getProperty(String attributeName, Object instance) {
        PropertyDescriptor pdName;
        try {
            pdName = new PropertyDescriptor(attributeName, instance.getClass());
            Method rmt = pdName.getReadMethod();
            return rmt.invoke(instance, new Object[] {});
        } catch (IntrospectionException e) {
            try {
                Field field = instance.getClass().getDeclaredField(attributeName);
                field.setAccessible(true);
                return field.get(instance);
            } catch (Exception e1) {
                logger.error("Failed to read the attribute:" + attributeName + " from " + instance, e);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to read the attribute:" + attributeName + " from " + instance, e);
            return null;
        }
    }

    public static void setProperty(String attributeName, Object value, Object instance) {
        PropertyDescriptor pdName;
        try {
            pdName = new PropertyDescriptor(attributeName, instance.getClass());
            Method rmt = pdName.getWriteMethod();
            rmt.setAccessible(true);
            rmt.invoke(instance, new Object[] { value });
        } catch (IntrospectionException e) {
            try {
                Field field = instance.getClass().getDeclaredField(attributeName);
                field.setAccessible(true);
                field.set(instance, value);
            } catch (Exception e1) {
                logger.error("Failed to write the attribute:" + attributeName + " of " + instance, e1);
            }
        } catch (Exception e) {
            logger.error("Failed to write the attribute:" + attributeName + " of " + instance, e);
        }
    }

    private static final Pattern NATURE_NUMBER = Pattern.compile("[\\d]*");
    private static final Pattern FLOAT_NUMBER = Pattern.compile("\\d+\\.\\d+");
    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("[true|false]+", Pattern.CASE_INSENSITIVE);

    /**
     * Parse the specifid value with type {@link String} to related one java type object
     * 
     * @param stringValue Orginal value string
     * @return if stringValue is number, return {@link Long}. if stringValue is digital number, return {@link Double}.
     *         if stringValue is true/false, return {@link Boolean}. Otherwise, return {@link String}
     */
    public static final Object parseType(String stringValue) {
        if (NATURE_NUMBER.matcher(stringValue).matches()) {
            return Long.parseLong(stringValue);
        } else if (FLOAT_NUMBER.matcher(stringValue).matches()) {
            return Double.parseDouble(stringValue);
        } else if (BOOLEAN_PATTERN.matcher(stringValue).matches()) {
            return CommonUtils.isTrue(stringValue);
        } else {
            return stringValue;
        }
    }
}
