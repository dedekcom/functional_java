/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import org.ddag.fun.FunObject;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface FunMatch {

  static boolean matchesOptOf(Object o, Object value) {
    if (o instanceof Optional && ((Optional)o).isPresent())  {
      Object optVal = ((Optional)o).get();
      return (value instanceof Class) ? ((Class)value).isInstance(optVal) : optVal.equals(value);
    } else
      return false;
  }

  /*
    Possible cases:
      if (caseObject(o, FunTuple.class))               return "tuple";
      else if ( matches(o, FunTuple.class, 5, String.class))               return "tuple2<Int: 5, String>";
      else if ( matches(o, 5) )                     return "int: 5";
      else if ( matches(o, FunMap.of()) )           return "empty map";
      else if ( matches(o, FunMap.class) )          return "map";
      else if ( matches(o, "x", FunList.of()) )     return "h::Nil";
      else if ( matches(o, "x", FunList.class) )    return "h::tail";
      else if ( matches(o, FunList.of()) )          return "Nil";
      else if ( matches(e, Integer.class))          return "int";
      else if ( matches(e, Optional.empty()))       return "Optional: empty";
      else if ( matches(e, Optional.class, Integer.class))     return "Optional<Integer>";
      else if ( matches(e, "x") )                   return "string: x";
   */
  static boolean matches(Object o, Object first, Object... params) {
    if (FunObject.class.isInstance(o))  {
      return ((FunObject)o).matches(first, params);
    } else if (params.length == 0) {
      return (first instanceof Class) ? ((Class) first).isInstance(o) : o.equals(first);
    } else {
      return params.length == 1 && first.equals(Optional.class) && matchesOptOf(o, params[0]);
    }
  }

  static <T> T match(Object o, Function<Object, T> caseFun) {    return caseFun.apply(o);  }

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

  /*
      matching case 2
   */
  static FunDoIf doIf(Function<Object, Object> executeIfMatches, Object firstPattern, Object... pattern) {
    return new FunDoIf(executeIfMatches, firstPattern, pattern);
  }

  static <R> R match(Object o, FunDoIf first, FunDoIf... cases) {
    Optional<Object> res = first.getOpt(o);
    if (res.isPresent()) return (R)res.get();
    for (FunDoIf c: cases) {
      res = c.getOpt(o);
      if (res.isPresent())
        return (R)res.get();
    }
    throw new FunMatchException();
  }

}
