/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

import functional.Fumeric;
import functional.FunList;

import java.util.Optional;

public class FunListTest {
  public static void testScalaList() {
    FunList<String> sl = FunList.of();
    sl.print();
    sl = FunList.of("a", "b", "2", "3", "d", "b", "2");
    sl.print();
    sl.distinct().print();

    FunList<Integer> i1 = FunList.of(1,2,5,-2,3,-10, 4);
    int sum = i1.fold(0, (acc, id) -> acc + id);
    System.out.println(sum);
    System.out.println(i1.sum());

    i1.partition(e -> e > 0).print();

    i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").print();

    sum = i1.foldLeft(0, (acc, id) -> acc + id);
    System.out.println(sum);

    int sum2 = sl.foldLeft(0, (acc, id) ->
            Fumeric.getInteger(id).map(intVal -> acc + intVal).orElse(acc)
    );
    System.out.println(sum2);
    sl.pushed("last or first").print();
    sl.tail().zipWithIndex().print();

    FunList.of(2).tail().print();

    FunList<Object> l2 = FunList.of(1, 2, Optional.of(5), Optional.empty(), FunList.of(1, 2, 3), FunList.of(new FunList<String>()));
    l2.flatten().splitAt(100).print();

    assert (l2.drop(20).isEmpty());
    l2.drop(3).print();

    l2.mkString("---").print();

  }

}
