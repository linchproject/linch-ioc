package test;

import com.linchproject.ioc.Component;

/**
 * @author Georg Schmidl
 */
public class A implements Component {
    private B b;

    private boolean init;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public boolean isInit() {
        return init;
    }

    @Override
    public void init() {
        init = true;
    }
}
