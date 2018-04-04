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

  static boolean caseOptOf(Object o, Object value) {
    if (o instanceof Optional && ((Optional)o).isPresent())  {
      Object optVal = ((Optional)o).get();
      return (value instanceof Class) ? ((Class)value).isInstance(optVal) : optVal.equals(value);
    } else
      return false;
  }

  /*
    Possible cases:
      if (caseObject(o, FunTuple.class))               return "tuple";
      else if ( caseObject(o, FunTuple.class, 5, String.class))               return "tuple2<Int: 5, String>";
      else if ( caseObject(o, 5) )                     return "int: 5";
      else if ( caseObject(o, FunMap.of()) )           return "empty map";
      else if ( caseObject(o, FunMap.class) )          return "map";
      else if ( caseObject(o, "x", FunList.of()) )     return "h::Nil";
      else if ( caseObject(o, "x", FunList.class) )    return "h::tail";
      else if ( caseObject(o, FunList.of()) )          return "Nil";
      else if ( caseObject(e, Integer.class))          return "int";
      else if ( caseObject(e, Optional.empty()))       return "Optional: empty";
      else if ( caseObject(e, Optional.class, Integer.class))     return "Optional<Integer>";
      else if ( caseObject(e, "x") )                   return "string: x";
   */
  static boolean caseObject(Object o, Object... params) {
    if (FunObject.class.isInstance(o))  {
      return ((FunObject)o).matches(params);
    } else if (params.length == 1) {
      return (params[0] instanceof Class) ? ((Class) params[0]).isInstance(o) : o.equals(params[0]);
    } else {
      return params.length == 2 && params[0].equals(Optional.class) && caseOptOf(o, params[1]);
    }
  }

  static Supplier<Boolean> caseOf(Object o, Object... params) { return () -> caseObject(o, params); }

  static <T> T match(Object o, Function<Object, T> caseFun) {
    return caseFun.apply(o);
  }

  static <T> T match(Supplier<Boolean> p1, Supplier<T> sup1, Supplier<Boolean> p2, Supplier<T> sup2) {
    if (p1.get()) return sup1.get();
    else if (p2.get()) return sup2.get();
    else throw new FunMatchException();
  }

  static <T> T match(Supplier<Boolean> p1, Supplier<T> sup1, Supplier<Boolean> p2, Supplier<T> sup2, Supplier<Boolean> p3, Supplier<T> sup3) {
    if (p1.get()) return sup1.get();
    else if (p2.get()) return sup2.get();
    else if (p3.get()) return sup3.get();
    else throw new FunMatchException();
  }

  static <T> T match(Supplier<Boolean> p1, Supplier<T> sup1, Supplier<Boolean> p2, Supplier<T> sup2, Supplier<Boolean> p3, Supplier<T> sup3,
                     Supplier<Boolean> p4, Supplier<T> sup4) {
    if (p1.get()) return sup1.get();
    else if (p2.get()) return sup2.get();
    else if (p3.get()) return sup3.get();
    else if (p4.get()) return sup4.get();
    else throw new FunMatchException();
  }

  static <T> T match(Supplier<Boolean> p1, Supplier<T> sup1, Supplier<Boolean> p2, Supplier<T> sup2, Supplier<Boolean> p3, Supplier<T> sup3,
                     Supplier<Boolean> p4, Supplier<T> sup4, Supplier<Boolean> p5, Supplier<T> sup5) {
    if (p1.get()) return sup1.get();
    else if (p2.get()) return sup2.get();
    else if (p3.get()) return sup3.get();
    else if (p4.get()) return sup4.get();
    else if (p5.get()) return sup5.get();
    else throw new FunMatchException();
  }


}
