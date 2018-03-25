/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import functional.FunList;
import functional.FunMap;
import functional.FunString;
import functional.Tuple2;
import functional.Tuple3;

import java.util.Optional;


public class MainApp {
  public static void main(String[] args) {
    testScalaList();
    testScalaMap();
    testScalaString();
    testScalaTuple();
  }

  public static void testScalaList() {
    FunList<String> sl = FunList.newList();
    sl.print();
    sl = FunList.newList("a", "b", "2", "3", "d", "b", "2");
    sl.print();
    sl.distinct().print();

    FunList<Integer> i1 = FunList.newList(1,2,5,-2,3,-10, 4);
    int sum = i1.fold(0, (acc, id) -> acc + id);
    System.out.println(sum);

    i1.partition(e -> e > 0).print();

    i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").print();

    sum = i1.foldLeft(0, (acc, id) -> acc + id);
    System.out.println(sum);

    int sum2 = sl.foldLeft(0, (acc, id) -> {
      try {
        return Integer.parseInt(id) + acc;
      } catch (Exception e) {
        return acc;
      }
    });
    System.out.println(sum2);
    sl.pushed("last or first").print();
    sl.tail().print();

    FunList.newList(2).tail().print();

    FunList<Object> l2 = FunList.newList(1, 2, Optional.of(5), Optional.empty(), FunList.newList(1, 2, 3), FunList.newList(new FunList<String>()));
    l2.flatten().print();

    l2.mkString("---").print();

  }

  public static void testScalaMap() {
    Tuple2<String, Integer> p = new Tuple2<>("k", 5);
    p.print();

    FunMap<String, Object> fm = FunMap.newMap(new Tuple2<>("ctyp", "fmtrait"), new Tuple2<>("almsum", 5));
    FunMap<String, Object> m = FunMap.newMap(new Tuple2<>("netype", "f8"), new Tuple2<>("name", "fsp3000c"),
            new Tuple2<>("fm", fm), new Tuple2<>("layers", FunList.newList("ots", "oms", "ety6")));
    m.print();

    FunMap<String,Object> m2 = m.transform((k, v) -> {
      if (v instanceof String && v == "f8") {
        return "F8";
      } else {
        return v;
      }
    });
    m2.print();

     assert ( m2.toList().map(pair -> pair._2()).equals(
            FunList.newList("F8", "fsp3000c", new FunMap<>(fm), FunList.newList("ots", "oms", "ety6") ) ) );

    FunMap<String, Object> fm2 = FunMap.newMap(new Tuple2<>("almsum", 5), new Tuple2<>("ctyp", "fmtrait"));

    assert(fm.equals(fm2));
  }

  public static void testScalaString() {
    FunString ss = new FunString("trUe");

    assert (ss.toBoolean());
    assert(ss.isBoolean());
    ss.set(10.22);
    assert(ss.isFloat());
    assert(ss.isDouble());
    assert(!ss.isInteger());
    assert(!ss.isBoolean());

    ss = FunString.newFunString(5, "k", Math.sqrt(3));
    ss.print();
  }

  public static void testScalaTuple() {
    FunString fs = new FunString();
    Tuple2<String, Integer> tp1 = new Tuple2<>("dd", 8);
    Tuple2<String, Integer> tp2 = new Tuple2<>("dd", 8);
    Tuple3<String, Integer, Character> tp3 = new Tuple3<>("dd", 8, 'c');
    assert (tp1.equals(tp2));
    assert (!tp1.equals(tp3));
    tp2.swap().print();
  }
}
