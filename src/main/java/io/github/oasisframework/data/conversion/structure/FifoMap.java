package io.github.oasisframework.data.conversion.structure;

import java.util.LinkedHashMap;

public class FifoMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;

	int max;

	public FifoMap(int max) {
		super(max + 1);
		this.max = max;

	}

	@Override
	public V put(K key, V value) {
		V forReturn = super.put(key, value);
		if (super.size() > max) {
			removeEldest();
		}

		return forReturn;
	}

	private void removeEldest() {
		for (K k : this.keySet()) {
			this.remove(k);
		}
	}

}
