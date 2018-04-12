/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

/*
  Each class that needs special pattern matching must implement this method
 */
public interface FunMatching {

  boolean matches(Object firstPattern, Object... restPatterns);

}
