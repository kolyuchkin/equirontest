package com.kavhome.equirontest.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
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
}