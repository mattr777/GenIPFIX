package com.richards777.genipfix;

public class IPFIXSetHeader {
    private short setID = 0;
    private short length = 0;
    public static final short TEMPLATE_SET = 2;

    public IPFIXSetHeader(short setID, short length) {
        this.setID = setID;
        this.length = length;
    }

    public short lengthInBytes() {
        return 4;
    }
}