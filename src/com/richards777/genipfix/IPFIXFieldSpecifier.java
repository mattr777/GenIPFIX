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

    public IPFIXFieldSpecifier(short informationElementID, short fieldLength, int enterpriseNumber) {
        this.informationElementID = (short)(informationElementID | 0x8000);
        this.fieldLength = fieldLength;
        this.enterpriseNumber = enterpriseNumber;
    }

    public short lengthInBytes() {
        if ((informationElementID & 0x8000) == 0x8000) {
            return (short) 8;
        } else {
            return (short) 4;
        }
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(informationElementID);
        b.putShort(fieldLength);
        if ((informationElementID & 0x8000) == 0x8000) {
            b.putInt(enterpriseNumber);
        }
        return b.array();
    }

    public short getFieldLength() {
        return fieldLength;
    }

    public short getInformationElementID() {
        return informationElementID;
    }


}
