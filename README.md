# functional_java
Library with some functional concepts:

1. Basic functional collections: interface FunList with lots of Scala-like methods, FunList implementations (FunLinkedList, FunUnmodifLinkedList, FunUnmodifArrayList), FunMap and Tuples.
2. Pattern matching.
3. Tail Recursion.

No pure functional approach, i.e. FunLinkedList derives from Java LinkedList and contains both mutable (with prefix 'm') and immutable methods. Programmer chooses which method and when to use. For some algorithms that need O(1) tail function there are special FunUnmodifLinkedList and FunUnmodifArrayList collections.
