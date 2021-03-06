package com.devexperts.dlcheck.util.test;

/*
 * #%L
 * core
 * %%
 * Copyright (C) 2015 - 2016 Devexperts, LLC
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.devexperts.dlcheck.util.LongObjWeakValuesHashMap;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class LongObjWeakValuesHashMapTest {
    private static final int N = 1000;
    private static final Object[] VALUES = new Object[N];
    private static final Random RANDOM = new Random(0);

    static {
        for (int i = 0; i < N; i++)
            VALUES[i] = new Object();
    }

    @Test
    public void testFunctionality() {
        LongObjWeakValuesHashMap<Object> mapActual = new LongObjWeakValuesHashMap<>();
        Map<Long, Object> mapExpected = new WeakHashMap<>();
        // Add elements
        for (int i = 0; i < N * 1000; i++) {
            long key = RANDOM.nextInt(N) + 1;
            Object val;
            if (RANDOM.nextInt(10) >= 9) {
                // Add element from OBJECTS
                val = VALUES[RANDOM.nextInt(N)];
            } else {
                // Add new element
                val = new Object();
            }
            mapActual.put(key, val);
            mapExpected.put(key, val);
        }
        // Check
        for (long key = 1; key <= N; key++) {
            Set<Object> values = new HashSet<>(Arrays.asList(VALUES));
            Object val = mapExpected.get(key);
            if (values.contains(val)) {
                assertEquals(val, mapActual.get(key));
            }
        }
    }
}