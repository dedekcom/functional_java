/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;

import org.ddag.fun.tuple.FunTuple;

@FunctionalInterface
public interface RecFun2<R, T> {

  FunTuple apply(R initialResult, T arg1);

}
