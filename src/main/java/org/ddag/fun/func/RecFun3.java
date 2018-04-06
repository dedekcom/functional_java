/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;

import org.ddag.fun.tuple.FunTuple;

@FunctionalInterface
public interface RecFun3<R, T1, T2> {

  FunTuple apply(R initialResult, T1 arg1, T2 arg2);

}
