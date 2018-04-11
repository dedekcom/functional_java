/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;


@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FunUnmodifLinkedList<T> extends AbstractList<T> implements FunList<T> {

  Node<T> first;

  public FunUnmodifLinkedList() {}

  public FunUnmodifLinkedList(FunUnmodifLinkedList list) {
    this.first = list.first;
  }

  public FunUnmodifLinkedList(Collection<? extends T> c) {
    Iterator<? extends T> it = c.iterator();
    Node<T> n = null;
    while (it.hasNext()) {
      if (n == null)  {
        n = new Node<>(it.next(), null);
        first = n;
      } else {
        n.next = new Node<>(it.next(), null);
        n = n.next;
      }
    }
  }

  private FunUnmodifLinkedList(Node<T> f) {
    this.first = f;
  }

  public boolean isEmpty() { return first == null; }

  public T head()           { return get(0);  }

  public FunList<T> added(T el) {
    Node<T> last = lastNode();
    Node<T> newNode = new Node<>(el, null);
    if (last == null) {
      first = newNode;
    } else {
      last.next = newNode;
    }
    return this;
  }

  public FunList<T> pushed(T el) {
    if (first == null) {
      return new FunUnmodifLinkedList<>(new Node<>(el, null));
    } else {
      return new FunUnmodifLinkedList<>(new Node<>(el, first));
    }
  }

  public FunList<T> removed(T el) {    return filterNot(e -> e.equals(el)).toUnmodifLinkedList();  }

  public FunList<T> reversed() {
    FunUnmodifLinkedList<T> result = new FunUnmodifLinkedList<>();
    for(T e: this) {
      result = (FunUnmodifLinkedList<T>)result.pushed(e);
    }
    return result;
  }

  private Node<T> node(int id)      {
    Node<T> f = first;
    int size = 0;
    while (f != null && size < id) {
      size++;
      f = f.next;
    }
    return f;
  }

  private Node<T> lastNode() {
    Node<T> f = first;
    Node<T> last = f;
    while (f != null) {
      last = f;
      f = f.next;
    }
    return last;
  }

  public T get(int id) {
    Node<T> f = this.node(id);
    if ( f == null )
      throw new IndexOutOfBoundsException();
    else return f.item;
  }

  public int size()         {
    Node<T> f = first;
    int size = 0;
    while (f != null) {
      size++;
      f = f.next;
    }
    return size;
  }

  public FunList<T> tail() {
    return new FunUnmodifLinkedList<>(first != null ? first.next : null);
  }

  public List<T> subList(int fromIndex, int toIndex) {
    return this.filterWithIndex((e, id) -> id >= fromIndex, toIndex);
  }

  public Object[] toArray() {
    Object[] objects = new Object[this.size()];
    int i = 0;
    for (T t: this) {
      objects[i] = t;
      i++;
    }
    return objects;
  }

  public boolean contains(Object object) {    return indexOf(object) != -1;  }

  @Override
  public Iterator<T> iterator() {    return this.listIterator();  }

  @Override
  public ListIterator<T> listIterator() {    return this.listIterator(0);  }

  public ListIterator<T> listIterator(int pos) {    return new ListItr(pos);  }

  /*
    Unsupported writeable methods
   */
  public void clear() { throw new UnsupportedOperationException(); }

  public boolean add(T e) { throw new UnsupportedOperationException(); }

  public void add(int pos, T e) { throw new UnsupportedOperationException(); }

  public boolean remove(Object o) { throw new UnsupportedOperationException(); }

  public boolean addAll(Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public boolean removeAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean retainAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean addAll(int n, Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public <R> R[] toArray(R[] a) { throw new UnsupportedOperationException(); }

  protected void removeRange(int fromIndex, int toIndex) {  throw new UnsupportedOperationException();  }


  private class ListItr implements ListIterator<T> {
    private Node<T> lastReturned;
    private Node<T> next;
    private int nextIndex;

    ListItr(int index) {
      next = node(index);
      nextIndex = index;
    }

    public boolean hasNext() {
      return next != null;
    }

    public T next() {
      if (!hasNext())
        throw new NoSuchElementException();

      lastReturned = next;
      next = next.next;
      nextIndex++;
      return lastReturned.item;
    }

    public boolean hasPrevious() {
      throw new UnsupportedOperationException();
    }

    public T previous() {      throw new UnsupportedOperationException();    }

    public int nextIndex() {
      return nextIndex;
    }

    public int previousIndex() {      return nextIndex - 1;    }

    public void remove()    { throw new UnsupportedOperationException();    }

    public void set(T e) {  throw new UnsupportedOperationException(); }

    public void add(T e) {  throw new UnsupportedOperationException();    }

    public void forEachRemaining(Consumer<? super T> action) {  throw new UnsupportedOperationException();    }
  }

  private static class Node<E> {
    E item;
    Node<E> next;

    Node(E element, Node<E> next) {
      this.item = element;
      this.next = next;
    }
  }
}
