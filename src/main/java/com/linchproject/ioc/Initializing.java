package com.linchproject.ioc;

/**
 * Interface for components that need to initialize after being
 * created and injection is completed.
 *
 * @author Georg Schmidl
 */
public interface Initializing {

    /**
     * Gets called after instance is created and autowiring is
     * completed.
     */
    void init();
}
