package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPv4Datagram implements EthernetPayload {
    private byte versionIHL = 0x45;
    private byte dsECN = 0;
    private short totalLength = 20;  // total length of IPv4 datagram in bytes
    private final short identification = 1;
    private final short flagsFragmentOffset = 0;
    private byte timeToLive = 64; // RFC1122 recommended value is 64
    private byte protocol = 17; // 17 = UDP, 6 = TCP
    private short headerChecksum = 0;
    private int srcIPAddress = 0xc0a8000e;
    private int destIPAddress = 0xc0a80033;
    private byte[] payload;

    public short getLengthInBytes() {
        return totalLength;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(totalLength);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.

        b.put(versionIHL);
        b.put(dsECN);
        b.putShort(totalLength);
        b.putShort(identification);
        b.putShort(flagsFragmentOffset);
        b.put(timeToLive);
        b.put(protocol);
        b.putShort(headerChecksum);
        b.putInt(srcIPAddress);
        b.putInt(destIPAddress);
// payload
        b.put(payload);
        return b.array();
    }

    public void setBuffer(byte[] buffer) {
        this.payload = buffer;
        totalLength = (short)payload.length;
        totalLength += 20; // header length
    }

}
