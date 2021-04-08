package com.kavhome.equirontest.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
class ThreadSafeCacheTest {
    @Test
    void computeTest() throws ExecutionException, InterruptedException, TimeoutException {
        final ThreadSafeCache<Integer, String> cache = ThreadSafeCache.of();
        AtomicBoolean computed = new AtomicBoolean(false);
        Function<Integer, String> function = integer -> {
            computed.compareAndSet(false, true);
            return integer.toString();
        };

        assertEquals("1", cache.compute(1, function).get(100, TimeUnit.MILLISECONDS));
        assertTrue(computed.get());
        computed.set(false);
        assertEquals("2", cache.compute(2, function).get(100, TimeUnit.MILLISECONDS));
        assertTrue(computed.get());
        computed.set(false);
        assertEquals("3", cache.compute(3, function).get(100, TimeUnit.MILLISECONDS));
        assertTrue(computed.get());
        computed.set(false);
        assertEquals("4", cache.compute(4, function).get(100, TimeUnit.MILLISECONDS));
        assertTrue(computed.get());
        computed.set(false);

        assertEquals("1", cache.compute(1, function).get(100, TimeUnit.MILLISECONDS));
        assertFalse(computed.get());
    }

    @Test
    void computeTestWithThreads() throws InterruptedException {
        final ThreadSafeCache<Integer, String> cache = ThreadSafeCache.of();
        AtomicInteger computed = new AtomicInteger(0);
        Function<Integer, String> function = integer -> {
            computed.incrementAndGet();
            return integer.toString();
        };

        Runnable runnable1 = () -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    final String s = cache.compute(i, function).get(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    throw new IllegalStateException("Incorrect algorithm");
                }
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 999; i > 0; i--) {
                try {
                    final String s = cache.compute(i, function).get(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    throw new IllegalStateException("Incorrect algorithm");
                }
            }
        };

        final Thread thread1 = new Thread(runnable1);
        final Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        thread1.join(10000);
        thread2.join(10000);

        assertEquals(1000, computed.get());
    }
}