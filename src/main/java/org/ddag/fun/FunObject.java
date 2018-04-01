/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

import java.util.Optional;

public interface FunObject {
  Object Any = Object.class;

  void print();

  boolean matches(Object... params);

  static boolean matchesOptionalOf(Object o, Object value) {
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

  static boolean matchesObject(Object o, Object... params) {
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
        return matchesOptionalOf(o, params[1]);
      } else
        return false;
    }
  }
}
