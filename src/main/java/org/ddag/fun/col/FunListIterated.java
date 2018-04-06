/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

@SuppressWarnings("unchecked")
public class FunListIterated<T> {
  private Object[] listCopy;
  private int idHead;

  FunListIterated(FunList<T> list) {
    listCopy = list.toArray();
    idHead = 0;
  }

  private FunListIterated(Object[] list, int index) {
    listCopy = list;
    idHead = index;
  }

  public boolean isEmpty() { return idHead >= listCopy.length; }

  public boolean nonEmpty() { return !isEmpty(); }

  public T head()           {
    return (T)listCopy[idHead];
  }

  public FunListIterated<T> tail() {
    return new FunListIterated<>(listCopy, idHead+1);
  }

  public FunList<T> toList() {
    FunList<T> list = new FunList<>();
    for(int i = idHead; i < listCopy.length; i++) {
      list.add((T)listCopy[i]);
    }
    return list;
  }

}
