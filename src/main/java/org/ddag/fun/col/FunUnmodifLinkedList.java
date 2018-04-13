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


/*
  Unmodified Linked List
  size()    : O(n)
  pushed(x) : O(1)
  added(x)  : O(1) for newly created instances, and O(n) for shared
      (last node allows to store sublists within list; adding element to the last position of the sublist is O(n))
  tail()    : O(1)
 */

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FunUnmodifLinkedList<T> extends AbstractList<T> implements FunList<T> {

  private Node<T> first;
  private Node<T> last;

  public FunUnmodifLinkedList() {}

  public FunUnmodifLinkedList(Collection<? extends T> c) {
    if (c instanceof FunUnmodifLinkedList && (
            ((FunUnmodifLinkedList) c).last == null
            || ((FunUnmodifLinkedList) c).last.next == null)) {
      this.first = ((FunUnmodifLinkedList)c).first;
      this.last = ((FunUnmodifLinkedList)c).last;
    } else {
      Iterator<? extends T> it = c.iterator();
      while (it.hasNext()) {
        if (last == null) {
          last = new Node<>(it.next(), null);
          first = last;
        } else {
          last.next = new Node<>(it.next(), null);
          last = last.next;
        }
      }
    }
  }

  private FunUnmodifLinkedList(Node<T> f, Node<T> l) {
    this.first = f;
    this.last = l == null ? f : l;
  }

  @Override
  public boolean isEmpty() { return first == null; }

  @Override
  public T head()           { return get(0);  }

  @Override
  public T last()   {
    if (last == null)
      throw new NoSuchElementException();
    return last.item;
  }

  @Override
  public FunList<T> added(T el) {
    Node<T> newNode = new Node<>(el, null);
    if (last == null) {
      return new FunUnmodifLinkedList<>(newNode, newNode);
    } else {
      if (last.next == null) {
        last.next = newNode;
        return new FunUnmodifLinkedList<>(first, last.next);
      } else {
        return new FunUnmodifLinkedList<T>().addedCol(this).added(el);
      }
    }
  }

  @Override
  public FunList<T> pushed(T el) {
    if (first == null) {
      return new FunUnmodifLinkedList<>(new Node<>(el, null), null);
    } else {
      return new FunUnmodifLinkedList<>(new Node<>(el, first), last);
    }
  }

  @Override
  public FunList<T> removed(T el) {    return filterNot(e -> e.equals(el)).toUnmodifLinkedList();  }

  @Override
  public FunList<T> reversed() {
    FunUnmodifLinkedList<T> result = new FunUnmodifLinkedList<>();
    for(T e: this) {
      result = (FunUnmodifLinkedList<T>)result.pushed(e);
    }
    return result;
  }

  @Override
  public FunList<T> addedCol(Collection<? extends T> col) {
    FunUnmodifLinkedList<T> list1 = new FunUnmodifLinkedList(this);
    FunUnmodifLinkedList<T> list2 = new FunUnmodifLinkedList<>(col);
    if (list1.last == null) return list2;
    list1.last.next = list2.first;
    list1.last = list2.last;
    return list1;
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

  @Override
  public T get(int id) {
    Node<T> f = this.node(id);
    if ( f == null )
      throw new NoSuchElementException();
    else return f.item;
  }

  @Override
  public int size()         {
    if (last == null)  return 0;
    Node<T> f = first;
    int size = 0;
    while (f != last.next) {
      size++;
      f = f.next;
    }
    return size;
  }

  @Override
  public FunList<T> tail() {
    Node<T> newFirst = first != last ? first.next : null;
    Node<T> newLast = newFirst == null ? null : last;
    return new FunUnmodifLinkedList<>(newFirst, newLast);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return this.slice(fromIndex, toIndex);
  }

  @Override
  public Object[] toArray() {
    Object[] objects = new Object[this.size()];
    int i = 0;
    for (T t: this) {
      objects[i] = t;
      i++;
    }
    return objects;
  }

  @Override
  public FunList<T> slice(int fromIndex, int toIndex) {
    int id = 0;
    if (fromIndex<0) fromIndex = 0;
    if (toIndex!=-1 && toIndex<=fromIndex)
      return new FunUnmodifLinkedList<>();
    Node<T> newFirst = null;
    Node<T> newLast = null;
    Node<T> n = first;
    while(n != null) {
      if (id == fromIndex)  {
        newFirst = n;
        newLast = n;
      }
      if (id == toIndex) {
        return new FunUnmodifLinkedList<>(newFirst, newLast);
      }
      newLast = n;
      n = n.next;
      id++;
    }
    return new FunUnmodifLinkedList<>(newFirst, newLast);
  }

  @Override
  public boolean contains(Object object) {    return indexOf(object) != -1;  }

  @Override
  public Iterator<T> iterator() {    return this.listIterator();  }

  @Override
  public ListIterator<T> listIterator() {    return this.listIterator(0);  }

  @Override
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
      return last!=null && next != last.next;
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
