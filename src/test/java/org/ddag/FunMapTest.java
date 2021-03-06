/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag;

import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.col.FunList;
import org.ddag.fun.col.FunMap;
import static org.ddag.fun.col.FunMap.Map;
import org.ddag.fun.match.FunMatch;
import static org.ddag.fun.match.FunMatch.getIf;
import static org.ddag.fun.tuple.FunTuple.T2;
import org.ddag.fun.tuple.Tuple2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunMapTest {

  @Test
  public void testScalaMap() {
    Tuple2<String, Integer> p = new Tuple2<>("k", 5);
    assertEquals(p.toString(), "(k, 5)");

    FunMap<String, Object> fm = FunMap.of(T2("ctyp", "fmtrait"), T2("almsum", 5));
    FunMap<String, Object> m = FunMap.of(T2("netype", "f8"), T2("name", "fsp3000c"),
            T2("fm", fm), T2("layers", FunList.of("ots", "oms", "ety6")));
    m.print();

    FunMap<String,Object> m2 = m.transform((k, v) -> {
      if (v instanceof String && v == "f8") {
        return "F8";
      } else {
        return v;
      }
    });
    m2.print();

    assertEquals( FunMap.of(T2("netype", "adv"), T2("name", "old"), T2("layers", "list") ),
      m.collect(
         getIf( v -> "adv", v -> FunList.of("f7","f8","f9").contains(v)),
         getIf( v -> "old", "fsp3000c"),
         getIf( v -> "list", FunList.class)
      ));

    assertTrue ( m2.toList().map(pair -> pair._2()).equals(
            FunList.of("F8", "fsp3000c", new FunMap<>(fm), FunList.of("ots", "oms", "ety6") ) ) );

    FunMap<String, Object> fm2 = FunMap.of(T2("almsum", 5), T2("ctyp", "fmtrait"));

    assertTrue(fm.equals(fm2));

    assertEquals( FunMap.of(T2("1", 1), T2("2", 2), T2("3",3)).filter((k,v ) -> v ==2 ),
            FunMap.of(T2("2", 2)) );

    assertEquals( FunMap.of(T2("1", 1), T2("2", 2), T2("3",3)).filterKeys( k -> FunList.of("2","5","7").contains(k)),
            FunMap.of(T2("2", 2)) );
  }

  @Test
  public void testMapMatches() {
    FunMap<String, Object> m = FunMap.of(T2("k1", 1), T2("k2", 2));

    assertEquals(FunMatch.match(m, o -> {
      if (FunMatch.matches(o, FunLinkedList.class)) return "list";
      else if (FunMatch.matches(o, 5)) return "int";
      else if (FunMatch.matches(o, FunMap.of())) return "empty map";
      else if (FunMatch.matches(o, FunMap.class)) return "map";
      else return "unknown";
    }), "map");

    assertEquals(FunMatch.match(new FunMap<String, Object>(), o -> {
      if (FunMatch.matches(o, FunLinkedList.class)) return "list";
      else if (FunMatch.matches(o, 5)) return "int";
      else if (FunMatch.matches(o, FunMap.of())) return "empty map";
      else if (FunMatch.matches(o, FunMap.class)) return "map";
      else return "unknown";
    }), "empty map");

    assertEquals(FunMap.of(), Map());
  }
}
