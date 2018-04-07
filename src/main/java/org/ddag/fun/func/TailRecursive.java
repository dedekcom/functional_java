/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public interface TailRecursive {

  static <R> R tailRec(R initialResult, Function<R, Result<R>> fun) {
    Result1<R> result = new Result1<>(true, initialResult);
    boolean continueLoop;
    do {
      Result<R> r = fun.apply(result.result);
      continueLoop = r.continueRecursion;
      if (continueLoop) {
        result = (Result1<R>)r;
      }
    } while (continueLoop);
    return result.result;
  }

  static <R, T> R tailRec(R initialResult, T arg1, RecFun2<R, T> fun) {
    Result2<R, T> result = new Result2<>(true, initialResult, arg1);
    boolean continueLoop;
    do {
      Result<R> r = fun.apply(result.result, result.arg1);
      continueLoop = r.continueRecursion;
      if (continueLoop) {
        result = (Result2<R, T>)r;
      }
    } while (continueLoop);
    return result.result;
  }

  static <R, T1, T2> R tailRec(R initialResult, T1 arg1, T2 arg2, RecFun3<R, T1, T2> fun) {
    Result3<R, T1, T2> result = new Result3<>(true, initialResult, arg1, arg2);
    boolean continueLoop;
    do {
      Result<R> r = fun.apply(result.result, result.arg1, result.arg2);
      continueLoop = r.continueRecursion;
      if (continueLoop) {
        result = (Result3<R, T1, T2>)r;
      }
    } while (continueLoop);
    return result.result;
  }

  static <R, T1, T2, T3> R tailRec(R initialResult, T1 arg1, T2 arg2, T3 arg3, RecFun4<R, T1, T2, T3> fun) {
    Result4<R, T1, T2, T3> result = new Result4<>(true, initialResult, arg1, arg2, arg3);
    boolean continueLoop;
    do {
      Result<R> r = fun.apply(result.result, result.arg1, result.arg2, result.arg3);
      continueLoop = r.continueRecursion;
      if (continueLoop) {
        result = (Result4<R, T1, T2, T3>)r;
      }
    } while (continueLoop);
    return result.result;
  }

  static <R> Result<R> Return(R result) {    return new Result1<>(false, result);  }

  static <R> Result<R> Continue(R result) {    return new Result1<>(true, result);  }

  static <R, T> Result<R> Continue(R result, T arg1) {  return new Result2<>(true, result, arg1);  }

  static <R, T1, T2> Result<R> Continue(R result, T1 arg1, T2 arg2) {
    return new Result3<>(true, result, arg1, arg2);
  }

  static <R, T1, T2, T3> Result<R> Continue(R result, T1 arg1, T2 arg2, T3 arg3) {
    return new Result4<>(true, result, arg1, arg2, arg3);
  }

  abstract class Result<R> {
    boolean continueRecursion;
    R result;

    Result(boolean bContinue, R result) {
      this.result = result;
      this.continueRecursion = bContinue;
    }
  }

  class Result1<R> extends Result<R> {
    Result1(boolean bContinue, R result) {
      super(bContinue, result);
    }
  }

  class Result2<R, T1> extends Result<R> {
    T1 arg1;
    Result2(boolean bContinue, R result, T1 arg1) {
      super(bContinue, result);
      this.arg1 = arg1;
    }
  }

  class Result3<R, T1, T2> extends Result<R> {
    T1 arg1;
    T2 arg2;
    Result3(boolean bContinue, R result, T1 arg1, T2 arg2) {
      super(bContinue, result);
      this.arg1 = arg1;
      this.arg2 = arg2;
    }
  }

  class Result4<R, T1, T2, T3> extends Result<R> {
    T1 arg1;
    T2 arg2;
    T3 arg3;
    Result4(boolean bContinue, R result, T1 arg1, T2 arg2, T3 arg3) {
      super(bContinue, result);
      this.arg1 = arg1;
      this.arg2 = arg2;
      this.arg3 = arg3;
    }
  }
}
