package test;

import com.linchproject.ioc.Component;

/**
 * @author Georg Schmidl
 */
public class A implements Component {
    private B b;

    private boolean init;
    private boolean destroy;

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

    @Override
    public void init() {
        init = true;
    }

    @Override
    public void destroy() {
        destroy = true;
    }
}
