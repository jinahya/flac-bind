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

package com.github.jinahya.flac.bind.metadata;


import com.github.jinahya.flac.bind.AbstractChild;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Track extends AbstractChild<Cuesheet> {


    public void read(final DataInput input) throws IOException {

        offset = input.readLong();
        number = input.readUnsignedByte();
        input.readFully(isrc = new byte[8]);

        final int b = input.readUnsignedByte();
        preEmphasis = ((b >> 6) & 0x01) == 0x01;
        audio = ((b >> 1) & 0x01) == 0x01;
        input.readFully(new byte[13]);

        getIndices().clear();
        final int count = input.readUnsignedByte();
        for (int i = 0; i < count; i++) {
            final Index index = new Index();
            index.setParent(this);
            index.read(input);
            getIndices().add(index);
        }
    }


    public void write(final DataOutput output) throws IOException {

        output.writeLong(offset);
        output.writeByte(number);
        output.write(new byte[8]);

        int b = (audio ? 0x01 : 0x00) << 1;
        b |= (preEmphasis ? 0x01 : 0x00) << 6;
        output.writeByte(b);
        output.write(new byte[13]);

        output.writeByte(getIndices().size());
        for (final Index index : getIndices()) {
            index.write(output);
        }
    }


    public Cuesheet getCuesheet() {

        return cuesheet;
    }


    public void setCuesheet(Cuesheet cuesheet) {

        this.cuesheet = cuesheet;
    }


    public List<Index> getIndices() {

        if (indices == null) {
            indices = new ArrayList<>();
        }

        return indices;
    }


    @Min(0L)
    private long offset;


    @Min(1)
    @Max(255)
    private int number;


    @Size(min = 12, max = 12)
    private byte[] isrc;


    private boolean audio;


    private boolean preEmphasis;


    private Cuesheet cuesheet;


    @Size(max = 255)
    private List<Index> indices;

}

