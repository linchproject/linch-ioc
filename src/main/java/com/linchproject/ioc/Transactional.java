package com.linchproject.ioc;

/**
 * Interface for components that need to act differently if an
 * error occurred before they are being destroyed.
 *
 * @author Georg Schmidl
 */
public interface Transactional {

    /**
     * Gets called before a component is removed from the
     * container and everything went well.
     */
    void succeed();

    /**
     * Gets called before a component is removed from the
     * container and an error occurred.
     */
    void fail();
}
