/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;


@FunctionalInterface
public interface RecFun2<R, T> {

  Result<R> apply(R initialResult, T arg1);

}
