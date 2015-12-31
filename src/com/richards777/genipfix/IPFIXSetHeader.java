package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXSetHeader {
    private short setID = 0;
    private short length = 4; // add length of this header
    public static final short TEMPLATE_SET = 2;

    public IPFIXSetHeader(short setID) {
        this.setID = setID;
    }
    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(setID);
        b.putShort(length);

        return b.array();
    }

    public void addRecordLength(short length) {
        this.length += length;
    }

    public short getSetLength() {
        return length;
    }

    public short lengthInBytes() {
        return 4;
    }
}