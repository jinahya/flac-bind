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
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Cuesheet extends Data {


    public void read(final DataInput input) throws IOException {

        input.readFully(catalog);
        samples = input.readLong();

        cd = ((input.readByte() >> 7) & 0x01) == 0x01;
        input.readFully(new byte[258]);

        getTracks().clear();
        final int trackCount = input.readUnsignedByte();
        for (int i = 0; i < trackCount; i++) {
            final Track track = new Track();
            track.setCuesheet(this);
            track.read(input);
            tracks.add(track);
        }
    }


    public void write(final DataOutput output) throws IOException {

        output.write(catalog);
        output.writeLong(samples);

        output.writeByte((cd ? 0x01 : 0x00) << 7);

        output.writeByte(getTracks().size());
        for (final Track track : getTracks()) {
            track.write(output);
        }
    }


    public List<Track> getTracks() {

        if (tracks == null) {
            tracks = new ArrayList<>();
        }

        return tracks;
    }


    @Size(min = 128, max = 128)
    private final byte[] catalog = new byte[128];


    @Min(0)
    private long samples;


    private boolean cd;


    private List<Track> tracks;

}

