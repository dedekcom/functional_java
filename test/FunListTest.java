/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

import functional.Fumeric;
import functional.FunList;
import functional.Tuple2;

import java.util.Optional;

public class FunListTest {

  public static void testFunList() {
    testBasicFunListOps();
    testFunListPerformance();
  }

  private static void testBasicFunListOps() {

    FunList<Integer> i1 = FunList.of(1,2,5,-2,3,-10, 4);
    assert( i1.fold(0, (acc, id) -> acc + id) == 3);
    assert ( i1.sum().intValue() == 3);
    assert (i1.partition(e -> e > 0)._2().equals(FunList.of(-2, -10)));

    assert (i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").equals(
            FunList.of("1k", "2k", "3k", "4k", "5k")));

    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");
    assert(sl.distinct().equals(FunList.of("a", "b", "2", "3", "d")));
    int sum2 = sl.foldLeft(0, (acc, id) ->
            Fumeric.getInteger(id).map(intVal -> acc + intVal).orElse(acc)
    );
    assert(sum2 == 7);
    assert (sl.pushed("new").head().equals("new"));
    assert (sl.tail().zipWithIndex().head().equals(new Tuple2<>("b", 0)));
    assert(FunList.of(2).tail().isEmpty());

    FunList<Object> l2 = FunList.of(1, 2, Optional.of(5), Optional.empty(), FunList.of(1, 2, 3), FunList.of(new FunList<String>()));
    assert(l2.flatten().splitAt(100)._1().equals(
            FunList.of(1, 2, 5, 1, 2, 3, new FunList<String>())
    ));

    assert (l2.drop(20).isEmpty());
    assert (l2.drop(3).size() == 3);
    assert (l2.slice(-5, 100).size() == l2.size());

    l2.mkString("---").print();

    assert(FunList.ofSize(5, 0).mapWithIndex((el, id) -> id).equals(FunList.of(0, 1, 2, 3, 4)));

  }

  private static void testFunListPerformance() {
    FunList<String> src = new FunList<>();
    for (int i=0; i<100000; i++) { src.add(Integer.toString(i)); }

    int loops = 10;
    System.out.println("\n\nLists performance:");
    Performance.testPerform("reversed.sorted", loops, () -> { new FunList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith", loops, () -> { new FunList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });

    Performance.testPerform("zipWithIndex.map.sum", loops, () -> { new FunList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

  }

}
