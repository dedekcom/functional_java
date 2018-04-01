/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

public interface FunObject {
  Object Any = Object.class;

  void print();

  boolean matches(Object... params);
}
