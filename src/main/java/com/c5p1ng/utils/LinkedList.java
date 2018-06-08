package com.c5p1ng.utils;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Serializable {
	
	private static final long serialVersionUID = -7507578051253879925L;

	transient int size = 0;

	transient int modCount = 0;
	
	transient Node<E> first;
	
	transient Node<E> last;
	
	public LinkedList() {
	}
	
	private void linkFirst(E e) {
		final Node<E> f = first;
		final Node<E> newNode = new Node<E>(null, e, f);
		first = newNode;
		if(f == null) {
			last = newNode;
		} else {
			f.prev = newNode;
		}
		size++;
		modCount++;
	}
	
	private void linkLast(E e) {
		final Node<E> l = last;
		final Node<E> newNode = new Node<E>(l, e, null);
		last = newNode;
		if(l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		size++;
		modCount++;
	}
	
	public E getFirst() {
		final Node<E> f = first;
		if(f == null) {
			throw new NoSuchElementException();
		}
		return f.item;
	}
	
	public E getLast() {
		final Node<E> l = last;
		if(l == null) {
			throw new NoSuchElementException();
		}
		return l.item;
	}
	
	private E unlinkFirst(Node<E> f) {
		final E element = f.item;
		final Node<E> next = f.next;
		f.item = null;
		f.next = null;
		first = next;
		if(next == null) {
			last = null;
		} else {
			next.prev = null;
		}
		size--;
		modCount++;
        return element;
	}
	
	private E unlinkLast(Node<E> l) {
		final E element = l.item;
		final Node<E> prev = l.prev;
		l.item = null;
		l.prev = null;
		last = prev;
		if(prev == null) {
			first = null;
		} else {
			prev.next = null;
		}
		size--;
		modCount++;
		return element;
	}
	
	private E unlink(Node<E> x) {
		final E element = x.item;
		final Node<E> next = x.next;
		final Node<E> prev = x.prev;
		
		if(prev == null) {
			first = next;
		} else {
			prev.next = next;
			x.prev = null;
		}
		
		if(next == null) {
			last = prev;
		} else {
			next.prev = prev;
			x.next = null;
		}
		x.item = null;
		size--;
		modCount++;
		return element;
	}
	
	public E removeFirst() {
		final Node<E> f = first;
		if(f == null) {
			throw new NoSuchElementException();
		}
		return unlinkFirst(f);
	}
	
	public E removeLast() {
		final Node<E> l = last;
		if(l == null) {
			throw new NoSuchElementException();
		}
		return unlinkLast(l);
	}
	
	public boolean remove(Object o) {
		if(o == null) {
			for(Node<E> x = first; x != null; x = x.next) {
				if(x.item == null) {
					unlink(x);
					return true;
				}
			}
		} else {
			for(Node<E> x = first; x != null; x = x.next) {
				if(o.equals(x.item)) {
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}
	
	public void addFirst(E e) {
		linkFirst(e);
	}
	
	public void addLast(E e) {
		linkLast(e);
	}
	
	public boolean add(E e) {
		linkLast(e);
		return true;
	}
	
	public int size() {
		return size;
	}
	
	public int indexOf(Object o) {
		int index = 0;
		if(o == null) {
			for(Node<E> x = first; x != null; x = x.next) {
				if(x.item == null) {
					return index;
				}
				index++;
			}
		} else {
			for(Node<E> x = first; x != null; x = x.next) {
				if(o.equals(x.item)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}
	
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}
	
	public void clear() {
		for(Node<E> x = first; x != null; ) {
			Node<E> next = x.next;
			x.item = null;
			x.next = null;
			x.prev = null;
			x = next;
		}
		first = last = null;
		size = 0;
		modCount++;
	}
	
	private static class Node<E> {
		E item;
		Node<E> prev;
		Node<E> next;
		
		Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
	}
}
