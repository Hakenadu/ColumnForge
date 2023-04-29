package com.github.hakenadu.columnforge.service.storage.limited;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedEntry<K> implements Delayed {
	private final K key;
	private final long removeTime;

	public DelayedEntry(K key, long lifetimeMillis) {
		this.key = key;
		this.removeTime = System.currentTimeMillis() + lifetimeMillis;
	}

	public DelayedEntry(K key) {
		this.key = key;
		this.removeTime = 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(removeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		return Long.compare(getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	public K getKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DelayedEntry) {
			return key.equals(((DelayedEntry<?>) obj).key);
		}
		return false;
	}
}
