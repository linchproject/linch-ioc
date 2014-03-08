package com.linchproject.ioc;

import org.junit.Test;
import test.A;
import test.B;

import static org.junit.Assert.*;

/**
 * @author Georg Schmidl
 */
public class ContainerTest {

    public class MyClass {
        private A a;
        private B b;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    @Test
    public void testGet() throws Exception {
        Container container = new Container();
        container.add("a", A.class);
        Object object = container.get("a");

        assertNotNull(object);
        assertTrue(object instanceof A);

        Object myComponent = new Object();
        container.add("myComponent", myComponent);
        assertEquals(myComponent, container.get("myComponent"));
    }

    @Test
    public void testInject() throws Exception {
        Container container = new Container();
        container.add("a", A.class);
        container.add("b", B.class);

        MyClass myClass = new MyClass();
        container.inject(myClass);

        assertNotNull(myClass.getA());
        assertNotNull(myClass.getA().getB());
        assertNotNull(myClass.getB());
        assertNotNull(myClass.getB().getA());
    }

    @Test
    public void testInit() throws Exception {
        Container container = new Container();
        container.add("a", A.class);

        A a = (A) container.get("a");
        assertTrue(a.isInit());

        container.add("a", new A());

        a = (A) container.get("a");
        assertTrue(a.isInit());
    }

    @Test
    public void testDestroy() throws Exception {
        Container container = new Container();
        container.add("a", A.class);

        A a = (A) container.get("a");
        assertFalse(a.isDestroy());

        container.add("a", A.class);
        assertTrue(a.isDestroy());

        a = (A) container.get("a");
        assertFalse(a.isDestroy());

        container.add("a", new A());
        assertTrue(a.isDestroy());
    }

    @Test
    public void testClear() throws Exception {
        Container container = new Container();
        container.add("a", A.class);

        A a = (A) container.get("a");
        assertFalse(a.isDestroy());

        container.clear();
        assertTrue(a.isDestroy());
    }

    @Test
    public void testTransactional() throws Exception {
        Container container = new Container();
        container.add("a", A.class);

        A a = (A) container.get("a");
        assertFalse(a.isBegin());
        assertFalse(a.isCommit());
        assertFalse(a.isRollback());

        container.begin();
        assertTrue(a.isBegin());
        assertFalse(a.isCommit());
        assertFalse(a.isRollback());

        container.commit();
        assertTrue(a.isBegin());
        assertTrue(a.isCommit());
        assertFalse(a.isRollback());

        container.rollback();
        assertTrue(a.isBegin());
        assertTrue(a.isCommit());
        assertTrue(a.isRollback());
    }
}
