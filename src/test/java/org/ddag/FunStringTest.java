/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag;

import org.ddag.fun.FunList;
import org.ddag.fun.FunString;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class FunStringTest {

  @Test
  public void testScalaString() {
    FunString ss = new FunString("trUe");

    assertTrue(ss.getBoolean().isPresent());
    ss.set(10.22);
    assertTrue(ss.getFloat().isPresent());
    assertTrue(ss.getDouble().isPresent());
    assertEquals (ss.getDouble().get(), Double.valueOf(10.22));
    assertTrue(!ss.getInteger().isPresent());
    assertTrue(!ss.getBoolean().isPresent());

    ss.set("/mit/me/1/eqh/shelf,1").split("/").drop(1).print();

    ss = FunString.of(5, "k", Math.sqrt(3));
    ss.print();

    ss.set("-0000009");
    assertTrue (ss.getInteger().isPresent());
    ss.set("0000009");
    assertTrue (ss.getInteger().isPresent());
    ss.set("0");
    assertTrue (ss.getInteger().isPresent());
    ss.set("-");
    assertTrue (!ss.getInteger().isPresent());
    ss.set("+");
    assertTrue (!ss.getInteger().isPresent());
    ss.set("9");
    assertTrue (ss.getInteger().isPresent());
    ss.set("-0000009");
    assertTrue (ss.getInteger().isPresent());
    ss.set("-111111111111111111111");
    assertTrue (!ss.getInteger().isPresent());
    ss.set(Integer.MAX_VALUE);
    assertTrue (ss.getInteger().isPresent());
    ss.set(Long.valueOf(Integer.MAX_VALUE)+1);
    assertTrue (!ss.getInteger().isPresent());
    ss.set(Integer.MIN_VALUE);
    assertTrue (ss.getInteger().isPresent());
    ss.set(Long.valueOf(Integer.MIN_VALUE)-1);
    assertTrue (!ss.getInteger().isPresent());
    ss.set("-00000000000000000" + Integer.MAX_VALUE);
    assertTrue (ss.getInteger().isPresent());
    ss.set("x09");
    assertTrue (!ss.getInteger().isPresent());
  }

  @Test
  public void testFunStringMatches() {
    FunString ss = new FunString(5);

    assertTrue(ss.matches(FunString.class));
    assertTrue(ss.matches(FunString.class, "5"));
    assertTrue(!ss.matches(FunString.class, 5));
    assertTrue(!ss.matches(FunList.class, "5"));
  }
}