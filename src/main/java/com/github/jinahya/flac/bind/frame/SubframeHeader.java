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
public class SubframeHeader {


    public static final int SUBFRAME_CONSTANT = 0b00000;


    public static final int SUBFRAME_VERBATIM = 0b00001;


    public void read(final BitInput input) throws IOException {

        final int padding = input.readUnsignedInt(1);
        type = input.readUnsignedInt(6);
        if (input.readBoolean()) {
            do {
                waisted++;
            } while (input.readUnsignedInt(1) == 0);
        }
    }


    public void write(final BitOutput output) throws IOException {

        output.writeUnsignedInt(1, 0);
        output.writeUnsignedInt(6, type);
        for (int i = 0; i < waisted - 1; i++) {
            output.writeUnsignedInt(1, 0);
        }
        output.writeUnsignedInt(1, 1);
    }


    public Subframe getSubframe() {

        return subframe;
    }


    public void setSubframe(final Subframe frame) {

        this.subframe = frame;
    }


    private int type;


    private int waisted;


    private Subframe subframe;

}

