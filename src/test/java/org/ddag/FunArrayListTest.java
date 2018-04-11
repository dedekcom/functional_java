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

public class FunArrayListTest {

  @Test
  public void testSublists() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

    assertEquals(FunList.of(3, 4, 5, 6, 7, 8, 9, 10).toUnmodifArrayList(), list.toUnmodifArrayList().tail().tail());

    assertEquals(FunList.of(4, 5, 6), list.toUnmodifArrayList().tail().tail().subList(1,4));

    assertEquals(FunList.of(), list.toUnmodifArrayList().subList(8,8));

    assertEquals(list.toUnmodifArrayList().toString(), list.toString());

    assertEquals(list.toUnmodifArrayList().filterNot(p -> p >= 2), list.subList(0, 1));

    assertTrue(!list.toUnmodifArrayList().contains(0));

    assertTrue(list.toUnmodifArrayList().containsAll(FunList.of(4,5,6,7)));

    assertTrue(list.toUnmodifArrayList().indexOf(3) == 2);

    assertEquals(FunList.of(3,4,5,6), FunList.of(4, 5, 6).toUnmodifArrayList().pushed(3));

    assertEquals(FunList.of(3,5,6), FunList.of(3, 4, 5, 6).toUnmodifArrayList().removed(4));
    assertEquals(FunList.of(4,5,6), FunList.of(3, 4, 5, 6).toUnmodifArrayList().removed(3));
    assertEquals(FunList.of(3,4,5), FunList.of(3, 4, 5, 6).toUnmodifArrayList().removed(6));

    assertEquals(FunList.of(1, 2, 3,4,5), FunList.of(5,4,3,2,1).toUnmodifArrayList().reversed());
  }

}
