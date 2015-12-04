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
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class PartitionedRice2 {


    public void read(final BitInput input) throws IOException {

        partitionOrder = input.readUnsignedInt(4);

        final int ricePartitionCount = (int) Math.pow(2, partitionOrder);
        for (int i = 0; i < ricePartitionCount; i++) {
            final Rice2Partition rice2Partition = new Rice2Partition();
            rice2Partition.setRasidualCodingMethodPartitionRice2(this);
            rice2Partition.read(input);
            getRice2Partitions().add(rice2Partition);
        }
    }


    public void write(final BitOutput output) throws IOException {

        output.writeUnsignedInt(4, partitionOrder);

        final int ricePartitionCount = (int) Math.pow(2, partitionOrder);
        for (int i = 0; i < ricePartitionCount; i++) {
            getRice2Partitions().get(i).write(output);
        }
    }


    public List<Rice2Partition> getRice2Partitions() {

        if (ricePartitions == null) {
            ricePartitions = new ArrayList<>();
        }

        return ricePartitions;
    }


    private int partitionOrder;


    private List<Rice2Partition> ricePartitions;

}

