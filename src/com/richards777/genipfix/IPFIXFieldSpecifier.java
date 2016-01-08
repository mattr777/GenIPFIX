package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXFieldSpecifier {
    private short informationElementID = 0;
    private short fieldLength = 0;
    private int enterpriseNumber = 0;

    // TODO: add support for enterprise defined fields

    public IPFIXFieldSpecifier(short elementID) {
        informationElementID = elementID;
        fieldLength = IPFIXInformationElements.get().getLengthInBytes(elementID);
    }

    public short lengthInBytes() {
        return (short) 4;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(informationElementID);
        b.putShort(fieldLength);
        return b.array();
    }

    public short getFieldLength() {
        return fieldLength;
    }

    public short getInformationElementID() {
        return informationElementID;
    }


}
