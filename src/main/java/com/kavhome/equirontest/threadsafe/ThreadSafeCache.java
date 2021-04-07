package com.kavhome.equirontest.threadsafe;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
@ThreadSafe
public final class ThreadSafeCache<K, V> {
    private final ReentrantLock lock = new ReentrantLock();
    private final ExecutorService executorService;

    @GuardedBy("lock")
    private final Map<K, V> map = new HashMap<>();

    public static <K,V> ThreadSafeCache<K, V> of() {
        return new ThreadSafeCache<>(Executors.newSingleThreadExecutor());
    }

    public static <K,V> ThreadSafeCache<K, V> of(@NotNull ExecutorService executorService) {
        return new ThreadSafeCache<>(requireNonNull(executorService));
    }

    private ThreadSafeCache(@NotNull ExecutorService executorService) {
        this.executorService = requireNonNull(executorService);
    }

    public Future<V> compute(@NotNull K k, @NotNull Function<K, V> f) {
        if (k == null || f == null) {
            return CompletableFuture.failedFuture(new NullPointerException("Key and Function must not be null"));
        }
        return executorService.submit(() -> {
            lock.lock();
            try {
                return map.computeIfAbsent(k, f);
            } finally {
                lock.unlock();
            }
        });
    }
}
