package org.github.avli.inheritance.multipleimpl;

public class Implementation extends Base implements FooProvider{
    @Override
    public void foo() {
        // Which one will be called: FooProvider.foo() or Base.foo()?
        super.foo();
    }

    public static void main(String[] args) {
        Implementation impl = new Implementation();
        // "Base.foo()" will be printed. Compare this with Kotlin overriding rules:
        // https://kotlinlang.org/docs/reference/classes.html#overriding-rules.
        impl.foo();
    }
}
