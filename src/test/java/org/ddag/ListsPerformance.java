/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.col.FunList;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ListsPerformance {

  @Test
  public void testLists() {
    FunLinkedList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));


  }

}
