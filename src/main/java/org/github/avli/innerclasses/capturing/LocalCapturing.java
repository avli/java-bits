package org.github.avli.innerclasses.capturing;

public class LocalCapturing {
    public static void main(String[] args) {
        // The code below won't compile. It seems innocent: we have a reference to an object in the outer scope
        // and try to assign a new value to it inside the `InnerA` class method. The problem is though captured
        // variable looks like the variable from the outer context, they are different variables! In other words,
        // if the code below were compiled, `s` from the outer scope would not change. We would simply made
        // the inner `s` to refer to the new string. To avoid possible confusion, Java simply prohibits such things
        // and enforces the usage of `final`.

//        String s = "foo";
//        class InnerA {
//            void f() {
//                s = "bar";
//            }
//        }

        // Compare the example above with this:

        String s = "foo";

        class InnerA {
            void f(String ref) {
                ref = "bar";
            }
        }

        (new InnerA()).f(s);

        // It compiles without an issue, though outer `s` still doesn't change after call to `f()`. But this time it's
        // not confusing. We pass a reference to a string to the method. In Java everything is passed by value, which
        // means the value of the reference is passed. This is a tricky part. `ref` is not the same thing as `s`.
        // `s` is a variable inside the `main()` scope containing a reference to the "foo" string. `ref`, in turn,
        // is a variable inside the `InnerA.f()` scope which refers to the same string. When assigning `ref` to "bar"
        // you simply make `ref` point to another object. This doesn't affect `s` anyhow.

        // Again, in case of a capture it *seems* like we are working with the same variable but actually we don't.
        // The same thing as passing a variable as a method parameter happens.

        // This also compiles:

        class InnerB {
            int f() {
                return s.trim().length();
            }
        }

        int lengthOfTrimmed =  (new InnerB()).f();

        // That's because `s` if *effectively final*, which means we don't mess up with the captured variable, e.g.
        // don't reassign it or something.


        // But why can't inner classes capture the actual variable from the outer scope?

        class InnerC {
            abstract class InnerD {
                abstract String f();
            }
            InnerD g() {
                String s = "Hello, World";
                return new InnerD() {
                    @Override
                    String f() {
                        // The inner variable `s` outlives the outer `s`!
                        return s;
                    }
                };
            }
        }
        InnerC.InnerD innerD = (new InnerC()).g();
        // If `s` in f() were the same as `s` in `g()` it would prevent the latest
        // from been garbage collected which could prevent in turn garbage collection of the
        // whole InnerC class instance from the expression above.
        innerD.f();
    }
}
