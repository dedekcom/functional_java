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

  static FunCaseOf caseOf(Object firstParam, Object... params) { return new FunCaseOf(firstParam, params); }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3,
                     FunCaseOf p4, Function<T, R> fun4) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else if (p4.get(o)) return fun4.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3,
                     FunCaseOf p4, Function<T, R> fun4, FunCaseOf p5, Function<T, R> fun5) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else if (p4.get(o)) return fun4.apply(o);
    else if (p5.get(o)) return fun5.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3,
                     FunCaseOf p4, Function<T, R> fun4, FunCaseOf p5, Function<T, R> fun5, FunCaseOf p6, Function<T, R> fun6) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else if (p4.get(o)) return fun4.apply(o);
    else if (p5.get(o)) return fun5.apply(o);
    else if (p6.get(o)) return fun6.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3,
                    FunCaseOf p4, Function<T, R> fun4, FunCaseOf p5, Function<T, R> fun5, FunCaseOf p6, Function<T, R> fun6,
                    FunCaseOf p7, Function<T, R> fun7) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else if (p4.get(o)) return fun4.apply(o);
    else if (p5.get(o)) return fun5.apply(o);
    else if (p6.get(o)) return fun6.apply(o);
    else if (p7.get(o)) return fun7.apply(o);
    else throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCaseOf p1, Function<T, R> fun1, FunCaseOf p2, Function<T, R> fun2, FunCaseOf p3, Function<T, R> fun3,
                     FunCaseOf p4, Function<T, R> fun4, FunCaseOf p5, Function<T, R> fun5, FunCaseOf p6, Function<T, R> fun6,
                     FunCaseOf p7, Function<T, R> fun7, FunCaseOf p8, Function<T, R> fun8) {
    if (p1.get(o)) return fun1.apply(o);
    else if (p2.get(o)) return fun2.apply(o);
    else if (p3.get(o)) return fun3.apply(o);
    else if (p4.get(o)) return fun4.apply(o);
    else if (p5.get(o)) return fun5.apply(o);
    else if (p6.get(o)) return fun6.apply(o);
    else if (p7.get(o)) return fun7.apply(o);
    else if (p8.get(o)) return fun8.apply(o);
    else throw new FunMatchException();
  }

  /*
      matching case 2
   */
  static FunCaseObject doIf(Function<Object, Object> executeIfMatches, Object firstPattern, Object... pattern) {
    return new FunCaseObject(executeIfMatches, firstPattern, pattern);
  }

  static <R> R match(Object o, FunCaseObject first, FunCaseObject... c) {
    Optional<Object> res = first.getOpt(o);
    if (res.isPresent()) return (R)res.get();
    for (int i=0; i<c.length; i++) {
      res = c[i].getOpt(o);
      if (res.isPresent())
        return (R)res.get();
    }
    throw new FunMatchException();
  }


  /*
      matching case 3
   */

  static <T, R> FunCase<T, R> Case(Function<T, R> executeIfMatches, Object firstPat, Object... pattern) {
    return new FunCase<>(executeIfMatches, firstPat, pattern);
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3, FunCase<T, R> c4) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c4.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3, FunCase<T, R> c4, FunCase<T, R> c5) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c4.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c5.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3, FunCase<T, R> c4, FunCase<T, R> c5,
                        FunCase<T, R> c6) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c4.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c5.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c6.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3, FunCase<T, R> c4, FunCase<T, R> c5,
                        FunCase<T, R> c6, FunCase<T, R> c7) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c4.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c5.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c6.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c7.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

  static <T, R> R match(T o, FunCase<T, R> c1, FunCase<T, R> c2, FunCase<T, R> c3, FunCase<T, R> c4, FunCase<T, R> c5,
                        FunCase<T, R> c6, FunCase<T, R> c7, FunCase<T, R> c8) {
    Optional<R> res = c1.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c2.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c3.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c4.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c5.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c6.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c7.getOpt(o);
    if (res.isPresent()) return res.get();
    res = c8.getOpt(o);
    if (res.isPresent()) return res.get();
    throw new FunMatchException();
  }

}
