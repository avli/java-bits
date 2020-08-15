package org.github.avli.inheritance.multipleimpl;

public class Implementation extends Base implements FooProvider{
    @Override
    public void foo() {
        // Which one will be called: FooProvider.foo() or Base.foo()?
        super.foo();
    }

    public static void main(String[] args) {
        Implementation impl = new Implementation();
        impl.foo(); // "Base.foo()" will be printed.
    }
}
