package com.linchproject.ioc;

/**
 * @author Georg Schmidl
 */
public interface Component {

    /**
     * Gets called after instance is created
     * and autowiring is completed.
     */
    void init();
}
