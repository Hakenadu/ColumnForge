package com.github.hakenadu.columnforge.service.storage.limited;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

public class ConcurrentMaxLifetimeMap<K, V> {

	private final Map<K, V> map = new ConcurrentHashMap<>();
	private final DelayQueue<DelayedEntry<K>> delayQueue = new DelayQueue<>();

	public void put(K key, V value, long lifetimeMillis) {
		V oldValue = map.put(key, value);
		if (oldValue != null) {
			// remove the old entry from the delayQueue
			delayQueue.remove(new DelayedEntry<>(key));
		}
		// add a new entry to the delayQueue
		delayQueue.put(new DelayedEntry<>(key, lifetimeMillis));
	}

	public V get(K key) {
		return map.get(key);
	}


	// thread to remove expired entries from the map
	private final Thread cleanupThread = new Thread(() -> {
		while (true) {
			try {
				DelayedEntry<K> entry = delayQueue.take();
				map.remove(entry.getKey());
			} catch (final InterruptedException e) {
				// ignore
			}
		}
	});

	public ConcurrentMaxLifetimeMap() {
		cleanupThread.setDaemon(true);
		cleanupThread.start();
	}
}
