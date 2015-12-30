package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXTemplateRecordHeader {
    private short templateID = 0;
    private short fieldCount = 0;

    public IPFIXTemplateRecordHeader(short templateID, short fieldCount) {
        this.templateID = templateID;
        this.fieldCount = fieldCount;
    }

    public short lengthInBytes() {
        return (short)4;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(templateID);
        b.putShort(fieldCount);
        return b.array();
    }

}
