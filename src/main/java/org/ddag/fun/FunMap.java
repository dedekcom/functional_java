/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

/*
  Scala-like Map collection

  methods starting with 'm' are mutable - it makes faster computing but causes side effects
 */

public class FunMap<K, V> extends LinkedHashMap<K, V> implements FunObject {
  private final static FunMap emptyMap = new FunMap();

  public FunMap() { super(); }

  public FunMap(Map<? extends K, ? extends V> m) { super(m); }

  public void print() {
    System.out.println(this.toString());
  }

  public <R> FunMap<K, R> transform(BiFunction<K, V, R> fun) {
    FunMap<K, R> m = new FunMap<>();
    this.forEach((k, v) -> m.put(k, fun.apply(k, v)));
    return m;
  }

  public FunList<Tuple2<K, V>> toList() {
    FunList<Tuple2<K,V>> list = new FunList<>();
    this.forEach((key, value) -> list.add(new Tuple2<>(key, value)));
    return list;
  }

  public FunMap<K, V> mUpdated(K key, V value) {
    this.put(key, value);
    return this;
  }

  public FunMap<K, V> mRemoved(K key) {
    this.remove(key);
    return this;
  }

  public boolean matches(Object... params) {
    if (params.length > 0 && (params[0] instanceof Class) && ((Class)params[0]).isInstance(this)) {
      return (params.length == 1) || (params.length == 2 && this.equals(params[1]));
    } else {
      return params.length == 1 && this.equals(params[0]);
    }
  }

  public FunMap<K, V> removed(K key) {
    return new FunMap<>(this).mRemoved(key);
  }

  public FunMap<K, V> updated(K key, V value) {
    return new FunMap<>(this).mUpdated(key, value);
  }

  public static FunMap of() { return emptyMap; }

  @SafeVarargs
  public static <K, V> FunMap<K, V> of(Tuple2<K, V>... params) {
    FunMap<K, V> m = new FunMap<>();
    Arrays.stream(params).forEach(e -> m.put(e._1(), e._2()));
    return m;
  }

}
