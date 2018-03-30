/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import functional.FunString;
import functional.Tuple2;
import functional.Tuple3;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FunTupleTest {

  @Test
  public void testScalaTuple() {
    FunString fs = new FunString();
    Tuple2<String, Integer> tp1 = new Tuple2<>("dd", 8);
    Tuple2<String, Integer> tp2 = new Tuple2<>("dd", 8);
    Tuple3<String, Integer, Character> tp3 = new Tuple3<>("dd", 8, 'c');
    assertTrue (tp1.equals(tp2));
    assertTrue (!tp1.equals(tp3));
    tp2.swap().print();
  }
}
