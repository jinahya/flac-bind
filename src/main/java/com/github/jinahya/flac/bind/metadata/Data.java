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


import com.github.jinahya.flac.bind.Stream;
import static java.util.Arrays.stream;
import javax.xml.bind.annotation.XmlTransient;
import static java.util.Arrays.stream;
import static java.util.Arrays.stream;
import static java.util.Arrays.stream;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@XmlTransient
public abstract class Data {


    public Block getMetadataBlock() {
        return metadataBlock;
    }


    public void setMetadataBlock(Block metadataBlock) {
        this.metadataBlock = metadataBlock;
    }


    private Block metadataBlock;

}

