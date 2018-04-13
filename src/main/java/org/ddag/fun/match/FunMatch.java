/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

  static boolean matches(Object o, Object firstPattern, Object... restPatterns) {
    if (FunMatching.class.isInstance(o))  {
      return ((FunMatching)o).matches(firstPattern, restPatterns);
    } else if (restPatterns.length == 0) {
      return (firstPattern instanceof Class) ? ((Class) firstPattern).isInstance(o) : o.equals(firstPattern);
    } else {
      return restPatterns.length == 1 && firstPattern.equals(Optional.class) && matchesOptOf(o, restPatterns[0]);
    }
  }

  /*
    simple matching with if
   */

  static <T, R> R match(T o, Function<T, R> caseFun) {    return caseFun.apply(o);  }

  /*
    generic matching
 */

  // apply function that returns result<R> if pattern matches
  static <T, R> FunGetIf<T, R> getIf(Function<T, R> executeIfMatches, Object firstPattern, Object... restPatterns) {
    return new FunGetIf<>(executeIfMatches, firstPattern, restPatterns);
  }

  static <T, R> FunGetIf<T, R> getIf(Function<T, R> executeIfMatches, Predicate<T> predicate) {
    return new FunGetIf<>(executeIfMatches, predicate);
  }

  @SafeVarargs
  static <T, R> R match(T o, FunGetIf<T, R> firstCase, FunGetIf<T, R>... restCases) {
    Optional<R> res = firstCase.getOpt(o);
    if (res.isPresent()) return res.get();
    for (FunGetIf<T, R> c: restCases) {
      res = c.getOpt(o);
      if (res.isPresent())
        return res.get();
    }
    throw new FunMatchException();
  }

  @SafeVarargs
  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> firstCase, FunGetIf<T, R>... restCases) {
    Optional<R> res = firstCase.getOpt(o);
    if (res.isPresent()) return res;
    for (FunGetIf<T, R> c: restCases) {
      res = c.getOpt(o);
      if (res.isPresent())
        return res;
    }
    return Optional.empty();
  }

  /*
  Needed for List.collect to suppress warnings
   */
  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1) {
    return case1.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    return case2.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    return case3.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
          FunGetIf<T, R> case4) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    return case4.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                         FunGetIf<T, R> case4, FunGetIf<T, R> case5) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    res = case4.getOpt(o);
    if (res.isPresent()) return res;
    return case5.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                         FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    res = case4.getOpt(o);
    if (res.isPresent()) return res;
    res = case5.getOpt(o);
    if (res.isPresent()) return res;
    return case6.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                             FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6, FunGetIf<T, R> case7) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    res = case4.getOpt(o);
    if (res.isPresent()) return res;
    res = case5.getOpt(o);
    if (res.isPresent()) return res;
    res = case6.getOpt(o);
    if (res.isPresent()) return res;
    return case7.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                         FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6,
                                         FunGetIf<T, R> case7, FunGetIf<T, R> case8) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    res = case4.getOpt(o);
    if (res.isPresent()) return res;
    res = case5.getOpt(o);
    if (res.isPresent()) return res;
    res = case6.getOpt(o);
    if (res.isPresent()) return res;
    res = case7.getOpt(o);
    if (res.isPresent()) return res;
    return case8.getOpt(o);
  }

  static <T, R> Optional<R> partialMatch(T o, FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                         FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6,
                                         FunGetIf<T, R> case7, FunGetIf<T, R> case8, FunGetIf<T, R> case9) {
    Optional<R> res = case1.getOpt(o);
    if (res.isPresent()) return res;
    res = case2.getOpt(o);
    if (res.isPresent()) return res;
    res = case3.getOpt(o);
    if (res.isPresent()) return res;
    res = case4.getOpt(o);
    if (res.isPresent()) return res;
    res = case5.getOpt(o);
    if (res.isPresent()) return res;
    res = case6.getOpt(o);
    if (res.isPresent()) return res;
    res = case7.getOpt(o);
    if (res.isPresent()) return res;
    res = case8.getOpt(o);
    if (res.isPresent()) return res;
    return case9.getOpt(o);
  }

  // execute void function if pattern matches
  static <T> FunRunIf<T> runIf(Consumer<T> executeIfMatches, Object firstPattern, Object... restPatterns) {
    return new FunRunIf<>(executeIfMatches, firstPattern, restPatterns);
  }

  @SafeVarargs
  static <T> void match(T o, FunRunIf<T> firstCase, FunRunIf<T>... nextCases) {
    boolean res = firstCase.get(o);
    if (res) return;
    for (FunRunIf<T> c: nextCases) {
      res = c.get(o);
      if (res)
        return;
    }
    throw new FunMatchException();
  }

  /*
    matching with types
   */

  // calling Case(...) is the only allowed way to create object FunCase
  static Supplier<FunCase> Case(Object firstPattern, Object... restPatterns) { return () -> new FunCase(firstPattern, restPatterns); }

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

  final class FunGetIf<T, R> {
    private Function<T, R> fun;
    private Predicate<T> pred;

    FunGetIf(Function<T, R> fun, Object firstPattern, Object... restPatterns) {
      this.fun = fun;
      this.pred = o -> FunMatch.matches(o, firstPattern, restPatterns);
    }

    FunGetIf(Function<T, R> fun, Predicate<T> predicate) {
      this.fun = fun;
      this.pred = predicate;
    }

    Optional<R> getOpt(T o) {
      return pred.test(o) ? Optional.of(fun.apply(o)) : Optional.empty();
    }
  }

  final class FunRunIf<T> {
    private Object first;
    private Object[] args;
    private Consumer<T> fun;

    FunRunIf(Consumer<T> fun, Object firstPattern, Object... restPatterns) {
      this.first = firstPattern;
      this.fun = fun;
      this.args = restPatterns;
    }

    boolean get(T o) {
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
