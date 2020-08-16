package org.github.avli.inheritance.classmembers;

/**
 * This example illustrates that Java class members are not polymorphic.
 */
public class ClassMembersAreNotPolymorphic {
    static class Parent {
        String s = "parent";
        void f() {
            System.out.println(s);
        }
    }

    static class Child extends Parent {
        String s = "child"; // This shadows `Parent.s`.
        void g() {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Child child = new Child();

        // Prints "child". So far so good.
        child.g();

        // Prints "parent", but shouldn't it print "child"?
        // Actually, no. The reason is Java class members are not polymorphic.
        // When calling `Child.f()`, a reference to the object is implicitly passed to the method, e.g.
        // `Child.f(this)`. But the "real" signature of `f()` is `f(Parent this)`, so inside `f()` call
        // `this.s` refers to the parent's `s`.
        child.f();
    }
}
