package com.gongpb.demo.junit.pownerMock;

public class PownerMockClass {
    final private int privateMethod(int a) {
        return a;
    }
    public int testPrivateMethod(int a) {
        return privateMethod(a);
    }

    public static int static_return_method() {
        return 1;
    }

    void voidMethod() {
        throw new IllegalStateException("should not go here");
    }

    public static void staticVoidMethod() {
        throw new IllegalStateException("should not go here");
    }

    public static void staticMethod(String a) {
        throw new IllegalStateException(a);
    }
}
