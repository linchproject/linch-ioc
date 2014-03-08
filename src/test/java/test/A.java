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
    private boolean succeed;
    private boolean fail;

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

    public boolean isSucceed() {
        return succeed;
    }

    public boolean isFail() {
        return fail;
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
    public void succeed() {
        succeed = true;
    }

    @Override
    public void fail() {
        fail = true;
    }
}
