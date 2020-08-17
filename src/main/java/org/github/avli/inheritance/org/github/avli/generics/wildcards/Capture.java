package org.github.avli.inheritance.org.github.avli.generics.wildcards;

public class Capture {

    static class Box<T> {
        T myObj;
        T get() {
            return myObj;
        }
        void put(T obj) {
            myObj = obj;
        }
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.put("foo");
        // We can do it since `Box<?>` is a superclass of `Box<String>`.
        // Wildcard captures allow us to bypass the fact that parametrized classes are not covariant,
        // e.g. `Box<Object>` is not a superclass of `Box<String>`.
        Box<?> pandoraBox = stringBox;
        // ...now what can we do with the class of type `Box<?>`?
        // `Box<?>` means that the type of the object the box contains is unknown and can be anything.
        // It means, it's illegal to do this:

        // String content = pandoraBox.get(); // <-- compilation error! `get()` returns capture of ?, not a string.

        // At the same time it's OK to do this:

        Object content = pandoraBox.get(); // It's fine because all type parameter must inherit from object.

        // This, in turn, can introduce possible cast exceptions since starting from here the compiler
        // cannot guarantee type safety. For example:

        try {
            Integer i = (Integer) content; // <-- compiles but causes cast exception.
        } catch (ClassCastException e) {
            System.out.println("Expected class cast exception.");
        }

        // It is also impossible to pass anything to the `put()` method since it requires the parameter
        // to be of type "capture of ?". You simply can't satisfy this requirement.
        // pandoraBox.put("bar"); <-- compile time error

        // And even though it's OK to refer to a wildcard capture with Object,
        // compiler still won't allow to *assign* objects to an object of a wildcard capture type, e.g.

        // pandoraBox.put(new Object()); // <-- compile time error
        // pandoraBox.myObj = new Object(); // <-- compile time error

        // The two assignments above violate type safety. Since the thing inside the box can be of *any* type,
        // it's not possible to assign anything to it. The same as you can't do stuff like
        // Object o = ...; // some initializer
        // String s = o; <-- compile time error: not every object is a string...
        // ...but every string is an object:
        // o = s; // It's fine.
    }
}
