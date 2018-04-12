/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*
  Set of static methods for pattern matching
 */
public interface FunMatch {

  static boolean matchesOptOf(Object o, Object value) {
    if (o instanceof Optional && ((Optional)o).isPresent())  {
      Object optVal = ((Optional)o).get();
      return (value instanceof Class) ? ((Class)value).isInstance(optVal) : optVal.equals(value);
    } else
      return false;
  }

  static boolean matches(Object o, Object first, Object... params) {
    if (FunMatching.class.isInstance(o))  {
      return ((FunMatching)o).matches(first, params);
    } else if (params.length == 0) {
      return (first instanceof Class) ? ((Class) first).isInstance(o) : o.equals(first);
    } else {
      return params.length == 1 && first.equals(Optional.class) && matchesOptOf(o, params[0]);
    }
  }

  /*
    simple matching with if
   */

  static <T, R> R match(T o, Function<T, R> caseFun) {    return caseFun.apply(o);  }

  /*
    generic matching
 */

  // apply function that returns Object if pattern matches
  static FunGetIf getIf(Function<Object, Object> executeIfMatches, Object firstPattern, Object... pattern) {
    return new FunGetIf(executeIfMatches, firstPattern, pattern);
  }

  @SuppressWarnings("unchecked")
  static <R> R match(Object o, FunGetIf firstCase, FunGetIf... nextCases) {
    Optional<Object> res = firstCase.getOpt(o);
    if (res.isPresent()) return (R)res.get();
    for (FunGetIf c: nextCases) {
      res = c.getOpt(o);
      if (res.isPresent())
        return (R)res.get();
    }
    throw new FunMatchException();
  }

  @SuppressWarnings("unchecked")
  static <R> Optional<R> partialMatch(Object o, FunGetIf firstCase, FunGetIf... nextCases) {
    Optional<Object> res = firstCase.getOpt(o);
    if (res.isPresent()) return Optional.of((R)res.get());
    for (FunGetIf c: nextCases) {
      res = c.getOpt(o);
      if (res.isPresent())
        return Optional.of((R)res.get());
    }
    return Optional.empty();
  }

  // execute void function if pattern matches
  static FunRunIf runIf(Consumer<Object> executeIfMatches, Object firstPattern, Object... pattern) {
    return new FunRunIf(executeIfMatches, firstPattern, pattern);
  }

  static void match(Object o, FunRunIf firstCase, FunRunIf... nextCases) {
    boolean res = firstCase.get(o);
    if (res) return;
    for (FunRunIf c: nextCases) {
      res = c.get(o);
      if (res) return;
    }
    throw new FunMatchException();
  }

  /*
    matching with types
   */

  // calling Case(...) is the only allowed way to create object FunCase
  static Supplier<FunCase> Case(Object firstParam, Object... params) { return () -> new FunCase(firstParam, params); }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5, Supplier<FunCase> p6, Function<T, R> fun6) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else if (p6.get().get(o)) return fun6.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5, Supplier<FunCase> p6, Function<T, R> fun6,
                        Supplier<FunCase> p7, Function<T, R> fun7) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else if (p6.get().get(o)) return fun6.apply(o);
    else if (p7.get().get(o)) return fun7.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5, Supplier<FunCase> p6, Function<T, R> fun6,
                        Supplier<FunCase> p7, Function<T, R> fun7, Supplier<FunCase> p8, Function<T, R> fun8) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else if (p6.get().get(o)) return fun6.apply(o);
    else if (p7.get().get(o)) return fun7.apply(o);
    else if (p8.get().get(o)) return fun8.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5, Supplier<FunCase> p6, Function<T, R> fun6,
                        Supplier<FunCase> p7, Function<T, R> fun7, Supplier<FunCase> p8, Function<T, R> fun8,
                        Supplier<FunCase> p9, Function<T, R> fun9) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else if (p6.get().get(o)) return fun6.apply(o);
    else if (p7.get().get(o)) return fun7.apply(o);
    else if (p8.get().get(o)) return fun8.apply(o);
    else if (p9.get().get(o)) return fun9.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, Supplier<FunCase> p1, Function<T, R> fun1, Supplier<FunCase> p2, Function<T, R> fun2,
                        Supplier<FunCase> p3, Function<T, R> fun3, Supplier<FunCase> p4, Function<T, R> fun4,
                        Supplier<FunCase> p5, Function<T, R> fun5, Supplier<FunCase> p6, Function<T, R> fun6,
                        Supplier<FunCase> p7, Function<T, R> fun7, Supplier<FunCase> p8, Function<T, R> fun8,
                        Supplier<FunCase> p9, Function<T, R> fun9, Supplier<FunCase> p10, Function<T, R> fun10) {
    if (p1.get().get(o)) return fun1.apply(o);
    else if (p2.get().get(o)) return fun2.apply(o);
    else if (p3.get().get(o)) return fun3.apply(o);
    else if (p4.get().get(o)) return fun4.apply(o);
    else if (p5.get().get(o)) return fun5.apply(o);
    else if (p6.get().get(o)) return fun6.apply(o);
    else if (p7.get().get(o)) return fun7.apply(o);
    else if (p8.get().get(o)) return fun8.apply(o);
    else if (p9.get().get(o)) return fun9.apply(o);
    else if (p10.get().get(o)) return fun10.apply(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4,
                        Supplier<FunCase> p5, Consumer<T> fun5) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else if (p5.get().get(o)) fun5.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4,
                        Supplier<FunCase> p5, Consumer<T> fun5, Supplier<FunCase> p6, Consumer<T> fun6) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else if (p5.get().get(o)) fun5.accept(o);
    else if (p6.get().get(o)) fun6.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4,
                        Supplier<FunCase> p5, Consumer<T> fun5, Supplier<FunCase> p6, Consumer<T> fun6,
                        Supplier<FunCase> p7, Consumer<T> fun7, Supplier<FunCase> p8, Consumer<T> fun8) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else if (p5.get().get(o)) fun5.accept(o);
    else if (p6.get().get(o)) fun6.accept(o);
    else if (p7.get().get(o)) fun7.accept(o);
    else if (p8.get().get(o)) fun8.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4,
                        Supplier<FunCase> p5, Consumer<T> fun5, Supplier<FunCase> p6, Consumer<T> fun6,
                        Supplier<FunCase> p7, Consumer<T> fun7, Supplier<FunCase> p8, Consumer<T> fun8,
                        Supplier<FunCase> p9, Consumer<T> fun9) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else if (p5.get().get(o)) fun5.accept(o);
    else if (p6.get().get(o)) fun6.accept(o);
    else if (p7.get().get(o)) fun7.accept(o);
    else if (p8.get().get(o)) fun8.accept(o);
    else if (p9.get().get(o)) fun9.accept(o);
    else throw new FunMatchException();
  }

  static <T> void match(T o, Supplier<FunCase> p1, Consumer<T> fun1, Supplier<FunCase> p2, Consumer<T> fun2,
                        Supplier<FunCase> p3, Consumer<T> fun3, Supplier<FunCase> p4, Consumer<T> fun4,
                        Supplier<FunCase> p5, Consumer<T> fun5, Supplier<FunCase> p6, Consumer<T> fun6,
                        Supplier<FunCase> p7, Consumer<T> fun7, Supplier<FunCase> p8, Consumer<T> fun8,
                        Supplier<FunCase> p9, Consumer<T> fun9, Supplier<FunCase> p10, Consumer<T> fun10) {
    if (p1.get().get(o)) fun1.accept(o);
    else if (p2.get().get(o)) fun2.accept(o);
    else if (p3.get().get(o)) fun3.accept(o);
    else if (p4.get().get(o)) fun4.accept(o);
    else if (p5.get().get(o)) fun5.accept(o);
    else if (p6.get().get(o)) fun6.accept(o);
    else if (p7.get().get(o)) fun7.accept(o);
    else if (p8.get().get(o)) fun8.accept(o);
    else if (p9.get().get(o)) fun9.accept(o);
    else if (p10.get().get(o)) fun10.accept(o);
    else throw new FunMatchException();
  }

  final class FunGetIf {
    private Object first;
    private Object[] args;
    private Function<Object, Object> fun;

    FunGetIf(Function<Object, Object> fun, Object firstArg, Object... args) {
      this.first = firstArg;
      this.fun = fun;
      this.args = args;
    }

    Optional<Object> getOpt(Object o) {
      return FunMatch.matches(o, first, args) ? Optional.of(fun.apply(o)) : Optional.empty();
    }
  }

  final class FunRunIf {
    private Object first;
    private Object[] args;
    private Consumer<Object> fun;

    FunRunIf(Consumer<Object> fun, Object firstArg, Object... args) {
      this.first = firstArg;
      this.fun = fun;
      this.args = args;
    }

    boolean get(Object o) {
      if (FunMatch.matches(o, first, args)) {
        fun.accept(o);
        return true;
      } else
        return false;
    }
  }

  final class FunCase {
    private Object first;
    private Object[] args;

    FunCase(Object firstArg, Object... args) {
      this.first = firstArg;
      this.args = args;
    }

    boolean get(Object o) {    return FunMatch.matches(o, first, args);  }
  }
}
