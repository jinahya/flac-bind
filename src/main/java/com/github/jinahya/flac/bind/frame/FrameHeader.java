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


import com.github.jinahya.flac.bind.metadata.StreamInfo;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class FrameHeader {


    public int getBlockSize() {
        return blockSize;
    }


    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }


    public static final int SYNC_CODE = 0b11111111111110;


    private static final long UTF8_MAX = 0xFFFFFFFFFL;


    public static final int BLOCK_STRATEGY_FIXED = 0;


    public static final int BLOCK_STRATEGY_VARIRABLE = 1;


    static long decodeUtf8(final DataInput input) throws IOException {

        final int b = input.readUnsignedByte();

        if (b <= 0b01111111) {
            return b;
        }

        final int count = Integer.numberOfLeadingZeros(~((byte) b)) - 24;
        long value = b & (0b01111111 >> count);
        for (int i = 1; i < count; i++) {
            value <<= 6;
            value |= input.readByte() & 0b00111111;
        }
        return value;
    }


    static void encodeUtf8(final long value, final DataOutput output)
        throws IOException {

        if (value <= 0b01111111) {
            output.write((int) value);
            return;
        }

        final int bits = Long.SIZE - Long.numberOfLeadingZeros(value);
        int q = bits / 6;
        if ((bits % 6) > 6 - q) {
            q++;
        }
        output.write((int) ((0b11111110 << (6 - q)) | (value >> (q * 6))));
        for (int i = (q - 1) * 6; i >= 0; i -= 6) {
            output.write((int) (0b10000000 | ((value >> i) & 0b00111111)));
        }
    }


    public void read(final DataInput input) throws IOException {

        {
            final int c = input.readUnsignedShort();
            assert (c >> 2) == SYNC_CODE;
            assert ((c >> 1) & 0b1) == 0b0;
            blockStrategy = c & 0b1;
        }
        {
            final int b = input.readUnsignedByte();
            blockSize = (b & 0b11110000) >> 4;
            sampleRate = b & 0b00001111;

        }
        {
            final int b = input.readUnsignedByte();
            channelAssignment = (b & 0b11110000) >> 4;
            sampleSize = (b & 0b00001110) >> 1;
            assert (b & 0b1) == 0b0;
        }
        if (blockStrategy == BLOCK_STRATEGY_VARIRABLE) {
            sampleNumber = decodeUtf8(input);
        } else {
            frameNumber = (int) decodeUtf8(input);
        }
        if (blockSize == 0b0110) {
            input.readUnsignedByte();
        } else if (blockSize == 0b0111) {
            input.readUnsignedShort();
        }
        if (sampleRate == 0b1100) {
            input.readUnsignedByte();
        } else if (sampleRate == 0b1101 || sampleRate == 0b1110
                   || sampleRate == 0b1111) {
            input.readUnsignedShort();
        }
        crc8 = input.readUnsignedByte();
    }


    public void write(final DataOutput output) throws IOException {
    }


    public int getBitsPerSample() {

        switch (sampleSize) {
            case 0b000:
                return ((StreamInfo) getFrame().getStream()
                        .getMetadataBlocks().get(0).getMetadataBlockData())
                    .getBitsPerSample();
            case 0b001:
                return 8;
            case 0b010:
                return 12;
            case 0b011:
                return 0;
            case 0b100:
                return 16;
            case 0b101:
                return 20;
            case 0b110:
                return 24;
            default:
                return 0;
        }
    }


    public Frame getFrame() {

        return frame;
    }


    public void setFrame(final Frame frame) {

        this.frame = frame;
    }


    @Min(0)
    @Max(1)
    private int blockStrategy;


    @Min(0x0)
    @Max(0xF)
    private int blockSize;


    @Min(0x0)
    @Max(0xF)
    private int sampleRate;


    @Min(0x0)
    @Max(0xF)
    private int channelAssignment;


    @Min(0b000)
    @Max(0b111)
    private int sampleSize;


    @Min(0L)
    @Max(UTF8_MAX)
    private Long sampleNumber;


    @Min(0)
    private Integer frameNumber;


    private int crc8;


    private Frame frame;

}

