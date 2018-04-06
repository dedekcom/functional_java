/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;


@FunctionalInterface
public interface RecFun3<R, T1, T2> {

  Result<R> apply(R initialResult, T1 arg1, T2 arg2);

}
