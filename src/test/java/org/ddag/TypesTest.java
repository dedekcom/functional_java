/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import org.ddag.fun.col.FunList;
import org.ddag.fun.col.FunMap;
import org.ddag.fun.col.FunUnmodifLinkedList;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.ddag.fun.tuple.FunTuple.T2;
import static org.ddag.fun.match.FunMatch.getIf;

import static junit.framework.TestCase.assertEquals;

public class TypesTest {

  interface IA {
    int get();
  }

  class A implements IA {
    int myVal;
    A(int v) { myVal = v; }
    public int get() { return myVal; }

    public boolean equals(Object o) {
      return (this == o) ||
              ((o instanceof IA) && this.get() == ((IA) o).get());
    }

    public String toString() {
      return "A(" + get() + ")";
    }
  }

  class B extends A {
    B(int v) {
      super(v);
      myVal++;
    }
    public int get() { return -myVal; }

    public String toString() {
      return "B(" + get() + ")";
    }
  }

  @Test
  public void testLists() {
    FunList<A> listA = FunList.of(new A(1), new B(2), new A(3));
    FunList<IA> listIA = FunList.of(new A(1), new B(2), new A(3));

    assertTrue(listA.equals(listIA));

    assertEquals(FunList.of(-3,1,3), listA.sortWith((e1, e2) -> e1.get() - e2.get()).map(A::get));
    assertEquals(FunList.of(-3,1,3), listIA.sortWith((e1, e2) -> e1.get() - e2.get()).map(IA::get));

    FunList<A> l1 = listA.collect(
            getIf(v -> v, v -> v.get() < 0),
            getIf(v -> new B(v.get()), A.class)
    );
    assertEquals(FunList.of(new B(1), new A(-3), new B(3)), l1 );

    FunList<A> l2 = listA.filter( v -> v.get() < 0);
    assertEquals(FunList.of(new A(-3)), l2 );

    assertEquals(listIA, new FunUnmodifLinkedList<>().pushed(new A(3)).pushed(new B(2)).pushed(new A(1)));
  }

  @Test
  public void testCmpMapsWithAbstracKeys() {
    FunMap<A, A> mapAA = FunMap.of( T2(new A(1), new A(1)), T2(new B(2), new B(2)),
            T2(new A(3), new B(3)), T2(new B(4), new A(4)));

    FunMap<A,Integer> mapTest1 = FunMap.of( T2(new B(2), 2), T2(new A(3), 3) );
    FunMap<A,Integer> mapTest2 = mapAA.collect(
            getIf( a -> -(a.get()+1), B.class)
    );
    assertEquals( mapTest1.toString(), mapTest2.toString() );   // can't compare maps with <A> keys
  }

  @Test
  public void testCmpMapsWithStandardKeys() {
    FunMap<Integer, A> mapAA = FunMap.of( T2(1, new A(1)), T2(2, new B(2)),
            T2(3, new B(3)), T2(4, new A(4)));

    assertEquals( FunMap.of( T2(2, 2), T2(3, 3) ),
            mapAA.collect(
                    getIf( a -> -(a.get()+1), B.class)
            ));

    assertEquals( FunMap.of( T2(1, 1), T2(2, 2), T2(3, 3), T2(4, 4) ),
            mapAA.collect(
                    getIf( a -> -(a.get()+1), B.class),
                    getIf( A::get, A.class)
            ));
  }

}
