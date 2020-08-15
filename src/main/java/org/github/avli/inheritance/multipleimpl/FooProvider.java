package org.github.avli.inheritance.multipleimpl;

public interface FooProvider {
    default void foo() {
        System.out.println("A.foo()");
    }
}
