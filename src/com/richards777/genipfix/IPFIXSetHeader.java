package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXSetHeader {
    private short setID = 0;
    private short length = 0;
    public static final short TEMPLATE_SET = 2;

    public IPFIXSetHeader(short setID, short lengthOfRecords) {
        this.setID = setID;
        this.length = lengthOfRecords;
        this.length += lengthInBytes();  // add length of this header
    }
    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(setID);
        b.putShort(length);

        return b.array();
    }

    public short lengthInBytes() {
        return 4;
    }
}