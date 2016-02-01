package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class UDPDatagram {
    private short srcPort = (short)0xFEED;
    private short destPort = 4739; // default port for IPFIX
    private short length = 8;
    private short checksum = 0;
    private byte[] payload;

    public UDPDatagram(short destPort) {
        this.destPort = destPort;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(length);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte payload is always BIG_ENDIAN.
        b.putShort(srcPort);
        b.putShort(destPort);
        b.putShort(length);
        b.putShort(checksum);
        b.put(payload);
        return b.array();
    }

    public void setPayload(byte[] buffer) {
        this.payload = buffer;
        length = (short)buffer.length;
        length += 8;
    }
}
