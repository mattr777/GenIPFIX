package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXSetHeader {
    private short setID = 0;
    private short lengthInBytes = 4; // add getLengthInBytes of this header
    public static final short TEMPLATE_SET = 2;

    public IPFIXSetHeader(short setID) {
        this.setID = setID;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(getLengthInBytes());
        b.putShort(setID);
        b.putShort(lengthInBytes);

        return b.array();
    }

    public void addRecordLength(short length) {
        this.lengthInBytes += length;
    }

    public short getSetLength() {
        return lengthInBytes;
    }

    public short getLengthInBytes() {
        return 4;
    }
}