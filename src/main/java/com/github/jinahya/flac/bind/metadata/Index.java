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
import javax.validation.constraints.Min;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Index extends AbstractChild<Track> {


    public void read(final DataInput input) throws IOException {

        offsetInSamples = input.readLong();
        indexPointNumber = input.readUnsignedByte();
        input.readFully(new byte[3]);
    }


    public void write(final DataOutput output) throws IOException {

        output.writeLong(offsetInSamples);
        output.writeByte(indexPointNumber);
        output.write(new byte[3]);
    }


    @Min(0L)
    private long offsetInSamples;


    @Min(0)
    private int indexPointNumber;

}

