package com.linchproject.ioc;

/**
 * Interface for components that need to clean up before getting
 * destroyed.
 *
 * @author Georg Schmidl
 */
public interface Destroyable {

    /**
     * Gets called when a component is removed from the
     * container.
     */
    void destroy();
}
