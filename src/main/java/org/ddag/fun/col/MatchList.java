/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.Iterator;
import java.util.List;

public interface MatchList {

  static <T> boolean matches(List<T> list, Object first, Object... params) {
    if (params.length == 0) {
      return ((first instanceof Class) && ((Class)first).isInstance(list)) || list.equals(first);
    } else {
      Iterator it = list.iterator();
      int last = params.length - 1;
      Object testOb = first;
      int i = 0;
      do {
        if (i >= list.size())
          return false;   // more elements in pattern than in a list
        Object n = it.next();
        if (testOb instanceof Class) {
          if (!((Class)testOb).isInstance(n))
            return false;
        }  else if (!n.equals(testOb))
          return false;
        testOb = params[i];
        i++;
      } while(i<=last);
      if (params[last].equals(FunList.Nil))  {   // test Nil on the last position of the pattern
        return !it.hasNext();
      } else {    // test tail
        return (params[last] instanceof Class && ((Class) params[last]).isInstance(list));
      }
    }
  }

}
