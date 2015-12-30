package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class UDPDatagram {
    private short srcPort = 0xFE;
    private short destPort = 4739; // default port for IPFIX
    private short length = 8;
    private short checksum = 0;
    private byte[] buffer;

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(length);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putShort(srcPort);
        b.putShort(destPort);
        b.putShort(length);
        b.putShort(checksum);
        b.put(buffer);
        return b.array();
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
        length = (short)buffer.length;
        length += 8;
    }
}
