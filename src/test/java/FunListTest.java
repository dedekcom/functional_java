/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import functional.Fumeric;
import functional.FunList;
import functional.Tuple2;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class FunListTest {

  @Test
  public void testBasicFunListOps() {

    FunList<Integer> i1 = FunList.of(1,2,5,-2,3,-10, 4);
    assertTrue( i1.fold(0, (acc, id) -> acc + id) == 3);
    assertTrue ( i1.sum().intValue() == 3);
    assertTrue (i1.partition(e -> e > 0)._2().equals(FunList.of(-2, -10)));

    assertTrue (i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").equals(
            FunList.of("1k", "2k", "3k", "4k", "5k")));

    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");
    assertTrue(sl.distinct().equals(FunList.of("a", "b", "2", "3", "d")));
    int sum2 = sl.foldLeft(0, (acc, id) ->
            Fumeric.getInteger(id).map(intVal -> acc + intVal).orElse(acc)
    );
    assertTrue(sum2 == 7);
    assertTrue (sl.pushed("new").head().equals("new"));
    assertTrue (sl.tail().zipWithIndex().head().equals(new Tuple2<>("b", 0)));
    assertTrue(FunList.of(2).tail().isEmpty());

    FunList<Object> l2 = FunList.of(1, 2, Optional.of(5), Optional.empty(), FunList.of(1, 2, 3), FunList.of(new FunList<String>()));
    assertTrue(l2.flatten().splitAt(100)._1().equals(
            FunList.of(1, 2, 5, 1, 2, 3, new FunList<String>())
    ));

    assertTrue (l2.drop(20).isEmpty());
    assertTrue (l2.drop(3).size() == 3);
    assertTrue (l2.slice(-5, 100).size() == l2.size());

    l2.mkString("---").print();

    assertTrue(FunList.ofSize(5, 0).mapWithIndex((el, id) -> id).equals(FunList.of(0, 1, 2, 3, 4)));

  }

  @Test
  public void testFunListPerformance() {
    FunList<String> src = new FunList<>();
    for (int i=0; i<100000; i++) { src.add(Integer.toString(i)); }

    int loops = 10;
    Performance.testPerform("\n\nPrepare FunList performance test", loops, () -> { ; });
    Performance.testPerform("reversed.sorted", loops, () -> { new FunList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith", loops, () -> { new FunList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });

    Performance.testPerform("zipWithIndex.map.sum", loops, () -> { new FunList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

  }

}
