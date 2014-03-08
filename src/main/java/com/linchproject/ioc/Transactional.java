package com.linchproject.ioc;

/**
 * Interface for components that need to act differently if an
 * error occurred before they are being destroyed.
 *
 * @author Georg Schmidl
 */
public interface Transactional {

    /**
     * Is called when the transaction begins.
     */
    void begin();

    /**
     * Is called when the transaction can be committed.
     */
    void commit();

    /**
     * Is called when the transaction should be rolled back.
     */
    void rollback();
}
