/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.Iterator;
import java.util.List;

public interface ListMatches {

  static <T> boolean matches(List<T> list, Object firstPattern, Object... restPatterns) {
    if (restPatterns.length == 0) {
      return firstPattern != null &&
              (((firstPattern instanceof Class) && ((Class)firstPattern).isInstance(list))
                      || list.equals(firstPattern));
    } else {
      Iterator it = list.iterator();
      int last = restPatterns.length - 1;
      Object testOb = firstPattern;
      int i = 0;
      do {
        if (i >= list.size())
          return false;   // more elements in pattern than in a list
        Object n = it.next();
        if (n==null)  {
          if (testOb != null)
            return false;
        } else if (testOb instanceof Class) {
          if (!((Class)testOb).isInstance(n))
            return false;
        }  else if (!n.equals(testOb))
          return false;
        testOb = restPatterns[i];
        i++;
      } while(i<=last);
      if (restPatterns[last] == null) {
        return false;
      } if (restPatterns[last].equals(FunLinkedList.Nil))  {   // test Nil on the last position of the pattern
        return !it.hasNext();
      } else {    // test tail
        return (restPatterns[last] instanceof Class && ((Class) restPatterns[last]).isInstance(list));
      }
    }
  }

}
