/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;


import org.ddag.fun.tuple.FunTuple;
import org.ddag.fun.tuple.Tuple2;
import org.ddag.fun.tuple.Tuple3;
import org.ddag.fun.tuple.Tuple4;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public interface TailRecursive {

  static <R> R tailRec(R initialResult, Function<R, FunTuple> fun) {
    Tuple2<Boolean, R> result = new Tuple2<>(true, initialResult);
    boolean continueLoop;
    do {
      FunTuple r = fun.apply(result._2());
      continueLoop = (Boolean)r._id(0);
      if (continueLoop) {
        result = (Tuple2<Boolean, R>)r;
      }
    } while (continueLoop);
    return result._2();
  }

  static <R, T> R tailRec(R initialResult, T arg1, RecFun2<R, T> fun) {
    Tuple3<Boolean, R, T> result = new Tuple3<>(true, initialResult, arg1);
    boolean continueLoop;
    do {
      FunTuple r = fun.apply(result._2(), result._3());
      continueLoop = (Boolean)r._id(0);
      if (continueLoop) {
        result = (Tuple3<Boolean, R, T>)r;
      }
    } while (continueLoop);
    return result._2();
  }

  static <R, T1, T2> R tailRec(R initialResult, T1 arg1, T2 arg2, RecFun3<R, T1, T2> fun) {
    Tuple4<Boolean, R, T1, T2> result = new Tuple4<>(true, initialResult, arg1, arg2);
    boolean continueLoop;
    do {
      FunTuple r = fun.apply(result._2(), result._3(), result._4());
      continueLoop = (Boolean)r._id(0);
      if (continueLoop) {
        result = (Tuple4<Boolean, R, T1, T2>)r;
      }
    } while (continueLoop);
    return result._2();
  }

  static <R> FunTuple Return(R result) {    return new Tuple2<>(false, result);  }

  static <R> FunTuple Continue(R result) {    return new Tuple2<>(true, result);  }

  static <R, T> FunTuple Continue(R result, T arg1) {  return new Tuple3<>(true, result, arg1);  }

  static <R, T1, T2> FunTuple Continue(R result, T1 arg1, T2 arg2) {    return new Tuple4<>(true, result, arg1, arg2);  }

}
