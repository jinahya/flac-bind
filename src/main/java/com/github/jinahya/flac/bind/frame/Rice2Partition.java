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


import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import java.io.IOException;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Rice2Partition {


    public void read(final BitInput input) throws IOException {

        encodingParameter = input.readUnsignedInt(5);
        if (encodingParameter == 0b11111) {
            bitsPerSample = input.readUnsignedInt(5);
        }
    }


    public void write(final BitOutput output) throws IOException {

        output.writeUnsignedInt(5, encodingParameter);
        if (encodingParameter == 0b11111) {
            output.writeUnsignedInt(5, bitsPerSample);
        }
    }


    public PartitionedRice2 getRasidualCodingMethodPartitionRice2() {
        return rasidualCodingMethodPartitionRice2;
    }


    public void setRasidualCodingMethodPartitionRice2(PartitionedRice2 rasidualCodingMethodPartitionRice2) {
        this.rasidualCodingMethodPartitionRice2 = rasidualCodingMethodPartitionRice2;
    }


    private int encodingParameter;


    private Integer bitsPerSample;


    private PartitionedRice2 rasidualCodingMethodPartitionRice2;

}

