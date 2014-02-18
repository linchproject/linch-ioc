package com.linchproject.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple IOC container that injects by setter injection.
 *
 * @author Georg Schmidl
 */
public class Container {

    private Map<String, Class<?>> classes;
    private Map<String, Object> objects;

    public Container() {
        this.classes = new HashMap<String, Class<?>>();
        this.objects = new HashMap<String, Object>();
    }

    /**
     * Adds a component to the container by given key.
     *
     * The setter method that will be called to inject the
     * instance of this component must match the key.
     *
     * For example, if your key is "myComponent", the
     * container will look for a setter "setMyComponent".
     *
     * @param key the unique component key
     * @param clazz the component class
     */
    public void add(String key, Class<?> clazz) {
        this.classes.put(key, clazz);
    }

    /**
     * Adds a component by object to the container by
     * given key.
     *
     * @param key
     * @param object
     */
    public void add(String key, Object object) {
        this.classes.put(key, object.getClass());
        this.objects.put(key, object);
        inject(object);
    }

    /**
     * Returns the component instance for given key.
     *
     * @param key the unique component key
     * @return instance of the component
     */
    public Object get(String key) {
        Object object = this.objects.get(key);
        if (object == null) {
            Class<?> clazz = classes.get(key);
            if (clazz != null) {
                try {
                    object = clazz.newInstance();
                    this.objects.put(key, object);
                    inject(object);
                    for (Class<?> interfaceClass : object.getClass().getInterfaces()) {
                        if (Component.class.equals(interfaceClass)) {
                            Method initMethod = object.getClass().getMethod("init");
                            initMethod.invoke(object);
                        }
                    }
                } catch (InstantiationException e) {
                    // ignore
                } catch (IllegalAccessException e) {
                    // ignore
                } catch (NoSuchMethodException e) {
                    // ignore
                } catch (InvocationTargetException e) {
                    // ignore
                }
            }
        }
        return object;
    }

    /**
     * Injects a given object by setter injection.
     *
     * @param object object to be injected
     */
    public void inject(Object object) {
        for (String key : this.classes.keySet()) {
            try {
                String setterName = getSetterName(key);
                for (Method method : object.getClass().getMethods()) {
                    if (setterName.equals(method.getName())) {
                        method.invoke(object, get(key));
                    }
                }

            } catch (InvocationTargetException e) {
                // ignore
            } catch (IllegalAccessException e) {
                // ignore
            }
        }
    }

    private String getSetterName(String key) {
        return "set" + key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
    }
}
