package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXTemplateRecordHeader {
    private short templateID = 0;
    private short fieldCount = 0;

    public IPFIXTemplateRecordHeader(short templateID) {
        this.templateID = templateID;
    }

    public void incrementFieldCount() {
        fieldCount++;
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

    public short getTemplateID() {
        return templateID;
    }

}
