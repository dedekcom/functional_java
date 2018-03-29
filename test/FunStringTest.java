/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

import functional.FunString;

public class FunStringTest {
  public static void testScalaString() {
    FunString ss = new FunString("trUe");

    assert(ss.getBoolean().isPresent());
    ss.set(10.22);
    assert(ss.getFloat().isPresent());
    assert(ss.getDouble().isPresent());
    assert(!ss.getInteger().isPresent());
    assert(!ss.getBoolean().isPresent());

    ss.set("/mit/me/1/eqh/shelf,1").split("/").drop(1).print();

    ss = FunString.of(5, "k", Math.sqrt(3));
    ss.print();

    ss.set("-0000009");
    assert (ss.getInteger().isPresent());
    ss.set("0000009");
    assert (ss.getInteger().isPresent());
    ss.set("0");
    assert (ss.getInteger().isPresent());
    ss.set("-");
    assert (!ss.getInteger().isPresent());
    ss.set("+");
    assert (!ss.getInteger().isPresent());
    ss.set("9");
    assert (ss.getInteger().isPresent());
    ss.set("-0000009");
    assert (ss.getInteger().isPresent());
    ss.set("-111111111111111111111");
    assert (!ss.getInteger().isPresent());
    ss.set(Integer.MAX_VALUE);
    assert (ss.getInteger().isPresent());
    ss.set(Long.valueOf(Integer.MAX_VALUE)+1);
    assert (!ss.getInteger().isPresent());
    ss.set(Integer.MIN_VALUE);
    assert (ss.getInteger().isPresent());
    ss.set(Long.valueOf(Integer.MIN_VALUE)-1);
    assert (!ss.getInteger().isPresent());
    ss.set("-00000000000000000" + Integer.MAX_VALUE);
    assert (ss.getInteger().isPresent());
    ss.set("x09");
    assert (!ss.getInteger().isPresent());
  }

}
