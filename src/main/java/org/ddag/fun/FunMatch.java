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
    if (o instanceof Optional)  {
      Optional<Object> opt = (Optional)o;
      if (opt.isPresent()) {
        if (value instanceof Class) {
          return ((Class)value).isInstance(opt.get());
        } else {
          return opt.get().equals(value);
        }
      } else {
        return false;
      }
    } else return false;
  }

  /*
    Possible cases:
      if (FunMatch.caseObject(o, FunTuple.class))               return "tuple";
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
    } else {
      if (params.length == 1) {
        if (params[0] instanceof Class) {
          return ((Class) params[0]).isInstance(o);
        } else {
          return o.equals(params[0]);
        }
      } else if (params.length == 2 && params[0].equals(Optional.class)) {
        return caseOptOf(o, params[1]);
      } else
        return false;
    }
  }

  static <T> T match(Object o, Function<Object, T> caseFun) {
    return caseFun.apply(o);
  }
}
