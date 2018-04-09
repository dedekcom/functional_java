/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.col.FunList;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SharedListTest {

  @Test
  public void testSublists() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

    assertEquals(FunList.of(3, 4, 5, 6, 7, 8, 9, 10).toSharedList(), list.toSharedList().tail().tail());

    assertEquals(FunList.of(4, 5, 6), list.toSharedList().tail().tail().subList(1,4));

    assertEquals(FunList.of(), list.toSharedList().subList(8,8));

    assertEquals(list.toSharedList().toString(), list.toString());

    assertEquals(list.toSharedList().filterNot(p -> p >= 2), list.subList(0, 1));

    assertTrue(!list.toSharedList().contains(0));

    assertTrue(list.toSharedList().containsAll(FunList.of(4,5,6,7)));

    assertTrue(list.toSharedList().indexOf(3) == 2);
  }

}
