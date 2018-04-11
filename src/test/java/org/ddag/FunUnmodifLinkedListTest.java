/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.col.FunList;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FunUnmodifLinkedListTest {

  @Test
  public void testSublists() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

    assertEquals(FunList.of(3, 4, 5, 6, 7, 8, 9, 10).toUnmodifLinkedList(), list.toUnmodifLinkedList().tail().tail());

    assertEquals(FunList.of(4, 5, 6), list.toUnmodifLinkedList().tail().tail().subList(1,4));

    assertEquals(FunList.of(), list.toUnmodifLinkedList().subList(8,8));

    assertEquals(list.toUnmodifLinkedList().toString(), list.toString());

    assertEquals(list.toUnmodifLinkedList().filterNot(p -> p >= 2), list.subList(0, 1));

    assertTrue(!list.toUnmodifLinkedList().contains(0));

    assertTrue(list.toUnmodifLinkedList().containsAll(FunList.of(4,5,6,7)));

    assertTrue(list.toUnmodifLinkedList().indexOf(3) == 2);

    assertEquals(FunList.of(3,4,5,6), FunList.of(4, 5, 6).toUnmodifLinkedList().pushed(3));

    assertEquals(FunList.of(3,5,6), FunList.of(3, 4, 5, 6).toUnmodifLinkedList().removed(4));
    assertEquals(FunList.of(4,5,6), FunList.of(3, 4, 5, 6).toUnmodifLinkedList().removed(3));
    assertEquals(FunList.of(3,4,5), FunList.of(3, 4, 5, 6).toUnmodifLinkedList().removed(6));

    assertEquals(FunList.of(1, 2, 3,4,5), FunList.of(5,4,3,2,1).toUnmodifLinkedList().reversed());
  }

  @Test(expected = NoSuchElementException.class)
  public void testLast() {
    FunList.of().toUnmodifLinkedList().last();
  }
}
