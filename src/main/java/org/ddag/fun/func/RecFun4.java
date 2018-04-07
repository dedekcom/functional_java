/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;


@FunctionalInterface
public interface RecFun4<R, T1, T2, T3> {

  TailRecursive.Result<R> apply(R initialResult, T1 arg1, T2 arg2, T3 arg3);

}
