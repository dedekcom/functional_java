/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun;

import java.util.Optional;
import java.util.function.Function;

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
      if (FunMatch.caseObject(o, FunTuple.class))               return "tuple";
      else if ( FunMatch.caseObject(o, FunTuple.class, 5, String.class))               return "tuple2<Int: 5, String>";
      else if ( FunMatch.caseObject(o, 5) )                     return "int: 5";
      else if ( FunMatch.caseObject(o, FunMap.of()) )           return "empty map";
      else if ( FunMatch.caseObject(o, FunMap.class) )          return "map";
      else if ( FunMatch.caseObject(o, FunList.class, "x", FunList.of()) ) return "h::Nil";
      else if ( FunMatch.caseObject(o, FunList.class, "x", FunList.class) ) return "h::tail";
      else if ( FunMatch.caseObject(o, FunList.of()) )          return "Nil";
      else if ( FunMatch.caseObject(e, Integer.class))          return "int";
      else if ( FunMatch.caseObject(e, Optional.empty()))       return "Optional: empty";
      else if ( FunMatch.caseObject(e, Optional.class, Integer.class))     return "Optional<Integer>";
      else if ( FunMatch.caseObject(e, "x") )                   return "string: x";
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

  static <T> T match(Object o, Function<Object, T> caseFun) {
    return caseFun.apply(o);
  }
}
