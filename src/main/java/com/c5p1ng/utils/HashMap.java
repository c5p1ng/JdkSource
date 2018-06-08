package com.c5p1ng.utils;

public class HashMap<V, K> {
	static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	static final int MAXIMUM_CAPACITY = 1 << 30;
	
	static final float DEFAULT_LOAD_FACTOR = 0.75F;
	
	transient Entry<K,V>[] table;
	
	transient int size;
	
	int threshold;
	
	final float loadFactor;
	
	transient int modCount;
	
	static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;
	
	@SuppressWarnings("unchecked")
	public HashMap(int initialCapacity, float loadFactor) {
		if(initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}
		if(initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}
		if(loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		}
		int capacity = 1;
		while(capacity < initialCapacity) {
			capacity <<= 1;
		}
		this.loadFactor = loadFactor;
		threshold = (int)Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
		table = new Entry[capacity];
	}
	
	public HashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}
	
	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	final int hash(Object k) {
		int h = 0;
		h ^= k.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		return h^(h >>> 7) ^ (h >>> 4);
	}
	
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(Object key) {
		if(key == null) {
			return getForNullKey();
		}
		Entry<K, V> entry = getEntry(key);
		return null == entry ? null : entry.getValue();
	}
	
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}
	
	public V put(K key, V value) {
		if(key == null) {
			return putForNullKey(value);
		}
		int hash = hash(key);
		int i = indexFor(hash, table.length);
		for(Entry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			if(e.hash == hash && ((k = e.key) == key || key.equals(k))) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}
		modCount++;
		addEntry(hash, key, value, i);
		return null;
	}
	
	private V putForNullKey(V value) {
		for(Entry<K, V> e = table[0]; e != null; e = e.next) {
			if(e.key == null) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}
		modCount++;
		addEntry(0, null, value, 0);
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void resize(int newCapacity) {
		Entry[] oldTable = table;
		int oldCapacity = oldTable.length;
		if(oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}
		Entry[] newTable = new Entry[newCapacity];
		transfer(newTable);
		table = newTable;
		threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void transfer(Entry[] newTable) {
		int newCapacity = newTable.length;
		for(Entry<K, V> e : table) {
			while(null != e) {
				Entry<K, V> next = e.next;
				int i = indexFor(e.hash, newCapacity);
				e.next = newTable[i];
				newTable[i] = e;
				e = next;
			}
		}
	}
	
	void addEntry(int hash, K key, V value, int bucketIndex) {
		if((size >= threshold) && (null != table[bucketIndex])) {
			resize(2 * table.length);
			hash = (null != key) ? hash(key) : 0;
			bucketIndex = indexFor(hash, table.length);
		}
		createEntry(hash, key, value, bucketIndex);
	}
	
	void createEntry(int hash, K key, V value, int bucketIndex) {
		Entry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new Entry<>(hash, key, value, e);
		size++;
	}
	
	final Entry<K, V> getEntry(Object key) {
		int hash = (key == null) ? 0 : hash(key);
		for(Entry<K, V> e = table[indexFor(hash, table.length)];
				e != null; e = e.next) {
			Object k;
			if(e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
				return e;
			}
		}
		return null;
	}
	
	public V getForNullKey() {
		for(Entry<K, V> e = table[0]; e != null; e = e.next) {
			if(e.key == null) {
				return e.value;
			}
		}
		return null;
	}
	
	static class Entry<K,V> {
		final K key;
		V value;
		Entry<K, V> next;
		int hash;
		
		public Entry(int h, K k, V v, Entry<K, V> n) {
			value = v;
			next = n;
			key = k;
			hash = h;
		}
		
		public final K getKey() {
			return key;
		}
		
		public final V getValue() {
			return value;
		}
		
		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
		
		public final int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^
					(value == null ? 0 : value.hashCode());
		}
		
		public final String toString() {
			return getKey() + "=" + getValue();
		}
	}
}
