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


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Picture extends Data {


    public void read(final DataInput input) throws IOException {

        type = input.readInt();
        {
            final byte[] bytes = new byte[input.readInt()];
            input.readFully(bytes);
            mime = new String(bytes, US_ASCII);
        }
        {
            final byte[] bytes = new byte[input.readInt()];
            input.readFully(bytes);
            description = new String(bytes, UTF_8);
        }
        width = input.readInt();
        height = input.readInt();
        depth = input.readInt();
        colors = input.readInt();
        {
            data = new byte[input.readInt()];
            input.readFully(data);
        }
    }


    public void write(final DataOutput output) throws IOException {

        output.writeInt(type);
        {
            final byte[] bytes = mime.getBytes(US_ASCII);
            output.writeInt(bytes.length);
            output.write(bytes);
        }
        {
            final byte[] bytes = description.getBytes(US_ASCII);
            output.writeInt(bytes.length);
            output.write(bytes);
        }
        output.writeInt(width);
        output.writeInt(height);
        output.writeInt(depth);
        output.writeInt(colors);
        {
            output.writeInt(data.length);
            output.write(data);
        }
    }


    private int type;


    private String mime;


    private String description;


    private int width;


    private int height;


    private int depth;


    private int colors;


    private byte[] data;

}

