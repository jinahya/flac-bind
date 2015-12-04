/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jinahya.flac.bind.frame;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static java.util.concurrent.ThreadLocalRandom.current;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.slf4j.Logger;
import org.testng.annotations.Test;
import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Utf8Test {


    private static final Logger logger = getLogger(Utf8Test.class);


    static final int MASK = 0b11111110;


    static long decode(final IntSupplier supplier) {

        final int b = supplier.getAsInt();

        if (b <= 0b01111111) {
            return b;
        }

        final int count = Integer.numberOfLeadingZeros(~((byte) b)) - 24;
        long value = b & (0b01111111 >> count);
        for (int i = 1; i < count; i++) {
            value <<= 6;
            value |= supplier.getAsInt() & 0b00111111;
        }
        return value;
    }


    static void encode(final long value, final IntConsumer consumer) {

        if (value <= 0b01111111) {
            consumer.accept((int) value);
            return;
        }

        final int bits = Long.SIZE - Long.numberOfLeadingZeros(value);
        int q = bits / 6;
        if ((bits % 6) > 6 - q) {
            q++;
        }
        consumer.accept((int) ((0b11111110 << (6 - q)) | (value >> (q * 6))));
        for (int i = (q - 1) * 6; i >= 0; i -= 6) {
            consumer.accept((int) (0b10000000 | ((value >> i) & 0b00111111)));
        }
    }


    private static void test(final long expected) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream(7);
        encode(expected, i -> output.write(i));
        final byte[] bytes = output.toByteArray();
        assertTrue(bytes.length <= 7);
        final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        final long actual = decode(() -> input.read());
        assertEquals(actual, expected);
    }


    @Test(invocationCount = 1024)
    public void test36() {

        test(current().nextLong(0x1000000000L));
    }


    @Test(invocationCount = 1024)
    public void test31() {

        test(current().nextInt() >>> 1);
    }

}

