/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

public interface FunObject {
  Object Any = Object.class;

  default void print() { System.out.println(this.toString()); }

}
