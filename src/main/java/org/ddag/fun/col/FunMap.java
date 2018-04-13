/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.FunObject;
import org.ddag.fun.match.FunMatch;
import org.ddag.fun.match.FunMatching;
import org.ddag.fun.tuple.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/*
  Scala-like Map collection

  methods starting with 'm' are mutable - it makes faster computing but causes side effects
 */

@SuppressWarnings("WeakerAccess")
public class FunMap<K, V> extends LinkedHashMap<K, V> implements FunObject, FunMatching {
  private static Map emptyMap = Collections.emptyMap();
  public static Map Map() { return emptyMap; }

  public FunMap() { super(); }

  public FunMap(Map<? extends K, ? extends V> m) { super(m); }

  public <R> FunMap<K, R> transform(BiFunction<? super K, ? super V, R> fun) {
    FunMap<K, R> m = new FunMap<>();
    this.forEach((k, v) -> m.put(k, fun.apply(k, v)));
    return m;
  }

  public FunMap<K, V> filter(BiFunction<? super K,? super V, Boolean> predicate) {
    FunMap<K, V> m = new FunMap<>();
    this.forEach((k, v) -> {
      if (predicate.apply(k, v))
        m.put(k, v);
    } );
    return m;
  }

  @SafeVarargs
  public final <R> FunMap<K, R> collect(FunMatch.FunGetIf<V, R> firstCase, FunMatch.FunGetIf<V, R>... restCases) {
    FunMap<K, R> m = new FunMap<>();
    this.forEach( (k, v) -> {
      Optional<R> res = FunMatch.partialMatch(v, firstCase, restCases);
      res.ifPresent(newV -> m.put(k, newV));
    } );
    return m;
  }

  public FunMap<K, V> filterKeys(Function<? super K, Boolean> predicate) {
    return this.filter((k, v) -> predicate.apply(k));
  }

  public FunLinkedList<Tuple2<K, V>> toList() {
    FunLinkedList<Tuple2<K,V>> list = new FunLinkedList<>();
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

  public boolean matches(Object firstPattern, Object... restPatterns) {
    return restPatterns.length == 0 && (
            ((firstPattern instanceof Class) && ((Class) firstPattern).isInstance(this)) ||
            this.equals(firstPattern)
    );
  }

  public FunMap<K, V> removed(K key) {
    return new FunMap<>(this).mRemoved(key);
  }

  public FunMap<K, V> updated(K key, V value) {
    return new FunMap<>(this).mUpdated(key, value);
  }

  public static FunMap of() { return new FunMap(); }

  @SafeVarargs
  public static <K, V> FunMap<K, V> of(Tuple2<K, V>... params) {
    FunMap<K, V> m = new FunMap<>();
    Arrays.stream(params).forEach(e -> m.put(e._1(), e._2()));
    return m;
  }

}
