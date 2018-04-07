# functional_java
Library with some functional concepts:

1. Basic functional collections (List, SharedList, Map) and Tuples.
2. Pattern matching.
3. Tail Recursion.

No pure functional approach, i.e. FunList derives from Java LinkedList and contains both mutable (with prefix 'm') and immutable methods. Programmer chooses which method and when to use. For some algorithms that need O(1) tail function there is a special SharedList collection.
