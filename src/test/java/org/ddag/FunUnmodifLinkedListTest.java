/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.col.FunList;
import org.ddag.fun.col.FunUnmodifLinkedList;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FunUnmodifLinkedListTest {

  @Test
  public void testSublists() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
    FunList<Integer> list2 = list.toUnmodifArrayList();

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

    assertEquals(list, list.toUnmodifLinkedList().slice(0,list.size()-2).added(9).added(10));
    assertEquals(list2, list);

    FunUnmodifLinkedList<Integer> l1 = FunList.of(1,2,3,4).toUnmodifLinkedList();
    FunList<Integer> l2 = l1.added(5);
    FunList<Integer> l3 = l1.added(6);
    FunList<Integer> l4 = l1.addedCol(FunList.of(5,6,7));
    assertEquals(FunList.of(1,2,3,4), l1);
    assertEquals(FunList.of(1,2,3,4,5), l2);
    assertEquals(FunList.of(1,2,3,4,6), l3);
    assertEquals(FunList.of(1,2,3,4,5,6,7), l4);
    assertEquals(FunList.of(), l1.tail().tail().tail().tail());
  }

  @Test(expected = NoSuchElementException.class)
  public void testLast() {
    FunList.of().toUnmodifLinkedList().last();
  }
}
