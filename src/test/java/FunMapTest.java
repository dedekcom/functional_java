/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import functional.FunList;
import functional.FunMap;
import functional.Tuple2;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FunMapTest {

  @Test
  public void testScalaMap() {
    Tuple2<String, Integer> p = new Tuple2<>("k", 5);
    p.print();

    FunMap<String, Object> fm = FunMap.of(new Tuple2<>("ctyp", "fmtrait"), new Tuple2<>("almsum", 5));
    FunMap<String, Object> m = FunMap.of(new Tuple2<>("netype", "f8"), new Tuple2<>("name", "fsp3000c"),
            new Tuple2<>("fm", fm), new Tuple2<>("layers", FunList.of("ots", "oms", "ety6")));
    m.print();

    FunMap<String,Object> m2 = m.transform((k, v) -> {
      if (v instanceof String && v == "f8") {
        return "F8";
      } else {
        return v;
      }
    });
    m2.print();

    assertTrue ( m2.toList().map(pair -> pair._2()).equals(
            FunList.of("F8", "fsp3000c", new FunMap<>(fm), FunList.of("ots", "oms", "ety6") ) ) );

    FunMap<String, Object> fm2 = FunMap.of(new Tuple2<>("almsum", 5), new Tuple2<>("ctyp", "fmtrait"));

    assertTrue(fm.equals(fm2));
  }
}
