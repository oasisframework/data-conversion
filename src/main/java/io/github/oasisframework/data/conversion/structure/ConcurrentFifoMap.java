package io.github.oasisframework.data.conversion.structure;

public class ConcurrentFifoMap<K, V> {
	private final FifoMap<K, V> fifoMap;
	private final int cacheSize;

	public ConcurrentFifoMap(int cacheSize) {
		this.cacheSize = cacheSize;
		fifoMap = new FifoMap<>(cacheSize);
	}

	public synchronized void put(K key, V value) {
		if (fifoMap.size() >= cacheSize && cacheSize > 0) {
			K oldAgedKey = fifoMap.keySet().iterator().next();
			fifoMap.remove(oldAgedKey);
		}
		fifoMap.put(key, value);
	}

	public V get(K key) {
		return fifoMap.get(key);
	}

	public boolean containsKey(K key) {
		return fifoMap.containsKey(key);
	}

	public synchronized V remove(K key) {
		return fifoMap.remove(key);
	}
}
