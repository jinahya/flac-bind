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


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Subframe {


    public SubframeHeader getSubframeHeader() {
        return subframeHeader;
    }


    public void setSubframeHeader(SubframeHeader subframeHeader) {
        this.subframeHeader = subframeHeader;
    }


    public SubframeData getSubframeData() {
        return subframeData;
    }


    public void setSubframeData(SubframeData subframeData) {
        this.subframeData = subframeData;
    }


    public Frame getFrame() {

        return frame;
    }


    public void setFrame(final Frame frame) {

        this.frame = frame;
    }


    private SubframeHeader subframeHeader;


    private SubframeData subframeData;


    private Frame frame;

}

