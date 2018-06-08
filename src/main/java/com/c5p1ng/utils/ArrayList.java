package com.c5p1ng.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class ArrayList<E> implements Serializable {
	
	private static final long serialVersionUID = 8683452581122892189L;
	
	private transient Object[] elementData;
	
	private int size;
	
	protected transient int modCount = 0;
	
	public ArrayList(int initialCapacity) {
		if(initialCapacity < 0) {
			throw new IllegalArgumentException("buneng fushu" + initialCapacity);
		}
		this.elementData = new Object[initialCapacity];
	}
	
	public ArrayList() {
		this(10);
	}
	
	public ArrayList(Collection<? extends E> c) {
		elementData = c.toArray();
		size = elementData.length;
		if(elementData.getClass() != Object[].class) {
			elementData = Arrays.copyOf(elementData, size, Object[].class);
		}
	}
	
	public void ensureCapacity(int minCapacity) {
		if(minCapacity > 0) {
			ensureCapacityInternal(minCapacity);
		}
	}
	
	private void ensureCapacityInternal(int minCapacity) {
		modCount++;
		if(minCapacity - elementData.length > 0) {
			grow(minCapacity);
		}
	}
	
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	
	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if(newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		if(newCapacity - MAX_ARRAY_SIZE > 0) {
			newCapacity = hugeCapacity(minCapacity);
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}
	
	private static int hugeCapacity(int minCapacity) {
		if(minCapacity < 0) {
			throw new OutOfMemoryError();
		}
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}
	
	public int indexOf(Object o) {
		if(o == null) {
			for(int i = 0; i < size; i++) {
				if(elementData[i] == null) {
					return i;
				}
			}
		} else {
			for(int i = 0; i < size; i++) {
				if(o.equals(elementData[i])) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int lastIndexOf(Object o) {
		if(o == null) {
			for(int i = size-1; i >= 0; i--) {
				if(elementData[i] == null) {
					return i;
				}
			}
		} else {
			for(int i = size-1; i >= 0; i--) {
				if(o.equals(elementData[i])) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public Object clone() {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<E> v = (ArrayList<E>) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			return v;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if(a.length < size) {
			return (T[]) Arrays.copyOf(elementData, size, a.getClass());
		}
		System.arraycopy(elementData, 0, a, 0, size);
		if(a.length > size) {
			a[size] = null;
		}
		return a;
	}
	
	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}
	
	public E get(int index) {
		rangeCheck(index);
		return elementData(index);
	}
	
	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}
	
	public boolean add(E e) {
		ensureCapacityInternal(size + 1);
		elementData[size++] = e;
		return true;
	}
	
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		ensureCapacityInternal(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}
	
	public E remove(int index) {
		rangeCheck(index);
		modCount++;
		E oldValue = elementData(index);
		int numMoved = size - index - 1;
		if(numMoved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);;
		}
		elementData[--size] = null;
		return oldValue;
	}
	
	public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
	
	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if(numMoved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);;
		}
		elementData[--size] = null;
	}
	
	public void clear() {
		modCount++;
		for(int i = 0; i < size; i++) {
			elementData[i] = null;
		}
		size = 0;
	}
	
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}
	
	private void rangeCheck(int index) {
		if(index >= size) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}
	}
	
	private void rangeCheckForAdd(int index) {
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}
}