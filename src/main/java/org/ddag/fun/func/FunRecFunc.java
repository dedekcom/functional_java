/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;


import org.ddag.fun.tuple.FunTuple;
import org.ddag.fun.tuple.Tuple6;

@SuppressWarnings("unchecked")
public interface FunRecFunc {

  static <R, T> R runRec(R initialResult, T arg1, RecFun2<R, T> fun) {
    Tuple6<Boolean, R, T, Object, Object, Object> result = new Tuple6<>(true, initialResult, arg1, null, null, null);
    boolean continueLoop;
    do {
      FunTuple r = fun.apply(result._2(), result._3());
      continueLoop = (Boolean)r._id(0);
      if (continueLoop) {
        result = (Tuple6<Boolean, R, T, Object, Object, Object>)r;
      }
    } while (continueLoop);
    return result._2();
  }

  static <R, T1, T2> R runRec(R initialResult, T1 arg1, T2 arg2, RecFun3<R, T1, T2> fun) {
    Tuple6<Boolean, R, T1, T2, Object, Object> result = new Tuple6<>(true, initialResult, arg1, arg2, null, null);
    boolean continueLoop;
    do {
      FunTuple r = fun.apply(result._2(), result._3(), result._4());
      continueLoop = (Boolean)r._id(0);
      if (continueLoop) {
        result = (Tuple6<Boolean, R, T1, T2, Object, Object>)r;
      }
    } while (continueLoop);
    return result._2();
  }

  static <R> FunTuple Return(R result) {
    return new Tuple6<>(false, result, null, null, null, null);
  }

  static <R, T> FunTuple Continue(R result, T arg1) {
    return new Tuple6<>(true, result, arg1, null, null, null);
  }

  static <R, T1, T2> FunTuple Continue(R result, T1 arg1, T2 arg2) {
    return new Tuple6<>(true, result, arg1, arg2, null, null);
  }

}
