package test;

import com.linchproject.ioc.Destroyable;
import com.linchproject.ioc.Initializing;
import com.linchproject.ioc.Transactional;

/**
 * @author Georg Schmidl
 */
public class A implements Initializing, Destroyable, Transactional {
    private B b;

    private boolean init;
    private boolean destroy;

    private boolean begin;
    private boolean commit;
    private boolean rollback;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public boolean isInit() {
        return init;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public boolean isBegin() {
        return begin;
    }

    public boolean isCommit() {
        return commit;
    }

    public boolean isRollback() {
        return rollback;
    }

    @Override
    public void init() {
        init = true;
    }

    @Override
    public void destroy() {
        destroy = true;
    }

    @Override
    public void begin() {
        begin = true;
    }

    @Override
    public void commit() {
        commit = true;
    }

    @Override
    public void rollback() {
        rollback = true;
    }
}
