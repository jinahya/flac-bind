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
public class PartitionedRice {


    public void read(final BitInput input) throws IOException {

        partitionOrder = input.readUnsignedInt(4);

        final int ricePartitionCount = (int) Math.pow(2, partitionOrder);
        for (int i = 0; i < ricePartitionCount; i++) {
            final RicePartition ricePartition = new RicePartition();
            ricePartition.setRasidualCodingMethodPartitionRice(this);
            ricePartition.read(input);
            getRicePartitions().add(ricePartition);
        }
    }


    public void write(final BitOutput output) throws IOException {

        output.writeUnsignedInt(4, partitionOrder);

        final int ricePartitionCount = (int) Math.pow(2, partitionOrder);
        for (int i = 0; i < ricePartitionCount; i++) {
            getRicePartitions().get(i).write(output);
        }
    }


    public List<RicePartition> getRicePartitions() {

        if (ricePartitions == null) {
            ricePartitions = new ArrayList<>();
        }

        return ricePartitions;
    }


    private int partitionOrder;


    private List<RicePartition> ricePartitions;

}

