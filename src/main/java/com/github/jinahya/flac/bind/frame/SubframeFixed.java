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
import java.io.IOException;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class SubframeFixed extends SubframeData {


    public void read(final BitInput input) throws IOException {

        warmUpSamples = input.readUnsignedInt(getSubframe().getFrame().getFrameHeader().getBitsPerSample());

        rasidual = new Rasidual();
        rasidual.read(input);
    }


    private int warmUpSamples;


    private Rasidual rasidual;

}

