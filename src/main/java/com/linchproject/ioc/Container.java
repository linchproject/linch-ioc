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

        Object existing = this.objects.get(key);
        if (existing != null) {
            destroy(existing);
            this.objects.remove(key);
        }
    }

    /**
     * Adds a component by object to the container by
     * given key.
     *
     * @param key the unique component key
     * @param object the component instance
     */
    public void add(String key, Object object) {
        this.classes.put(key, object.getClass());

        Object existing = this.objects.get(key);
        if (existing != null) {
            destroy(existing);
        }

        this.objects.put(key, object);
        inject(object);
        init(object);
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
            Class<?> clazz = this.classes.get(key);
            if (clazz != null) {
                try {
                    object = clazz.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                this.objects.put(key, object);
                inject(object);
                init(object);
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

            } catch (IllegalAccessException e) {
                // ignore
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    /**
     * Removes all components.
     */
    public void clear() {
        this.classes.clear();

        for (Object object : this.objects.values()) {
            destroy(object);
        }
        this.objects.clear();
    }

    /**
     * Tells all transactional components that a transaction begins.
     */
    public void begin() {
        for (Object object : this.objects.values()) {
            invokeComponentMethod(object, Transactional.class, "begin");
        }
    }

    /**
     * Tells all transactional components that a transaction can be committed.
     */
    public void commit() {
        for (Object object : this.objects.values()) {
            invokeComponentMethod(object, Transactional.class, "commit");
        }
    }

    /**
     * Tells all transactional components that a transaction should be rolled bacl.
     */
    public void rollback() {
        for (Object object : this.objects.values()) {
            invokeComponentMethod(object, Transactional.class, "rollback");
        }
    }

    private void init(Object object) {
        invokeComponentMethod(object, Initializing.class, "init");
    }

    private void destroy(Object object) {
        invokeComponentMethod(object, Destroyable.class, "destroy");
    }

    private void invokeComponentMethod(Object object, Class<?> clazz, String methodName) {
        for (Class<?> interfaceClass : object.getClass().getInterfaces()) {
            if (clazz.equals(interfaceClass)) {
                try {
                    Method method = object.getClass().getMethod(methodName);
                    method.invoke(object);
                } catch (NoSuchMethodException e) {
                    // ignore
                } catch (IllegalAccessException e) {
                    // ignore
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    private String getSetterName(String key) {
        return "set" + key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
    }
}
