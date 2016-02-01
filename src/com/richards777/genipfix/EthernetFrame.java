package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class EthernetFrame {
    private int destMAC1 = 0x080027aa;
    private short destMAC2 = (short)0x6fff;
    private int srcMAC1 = 0x080027b5;
    private short srcMAC2 = (short)0x56ee;
    private short lengthOrType = (short) 0x0800; // IPv4 = 0800, IPv6 = 86DD, ARP = 0806
    private EthernetPayload ethernetPayload;
    private CRC32 fcs = new CRC32();

    private int frameLength = 0;

    public int getLengthInBytes() {
        return frameLength;
    }

    public byte[] getBuffer() {
        ByteBuffer ethBuffer = ByteBuffer.allocate(frameLength);
        ByteBuffer ethBufferCopy = ByteBuffer.allocate(frameLength-4);
//ethBuffer.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        ethBuffer.putInt(destMAC1);
        ethBufferCopy.putInt(destMAC1);

        ethBuffer.putShort(destMAC2);
        ethBufferCopy.putShort(destMAC2);

        ethBuffer.putInt(srcMAC1);
        ethBufferCopy.putInt(srcMAC1);

        ethBuffer.putShort(srcMAC2);
        ethBufferCopy.putShort(srcMAC2);

        ethBuffer.putShort(lengthOrType);
        ethBufferCopy.putShort(lengthOrType);

// payload
        short payloadLength = ethernetPayload.getLengthInBytes();
        if (payloadLength >= 46) {
            ethBuffer.put(ethernetPayload.getBuffer());
            ethBufferCopy.put(ethernetPayload.getBuffer());
        } else {
            int padLength = 46 - payloadLength;
            ByteBuffer payloadBuffer = ByteBuffer.allocate(64);
            byte[] pad = new byte[padLength];
            payloadBuffer.put(ethernetPayload.getBuffer());
            payloadBuffer.put(pad);
            ethBuffer.put(payloadBuffer.array());
            ethBufferCopy.put(payloadBuffer.array());
        }
        fcs.update(ethBufferCopy.array());
        int fcsValue = (int)fcs.getValue();
        ethBuffer.putInt(Integer.reverseBytes(fcsValue));
        return ethBuffer.array();
    }

    public void setPayload(EthernetPayload ethernetPayload) {
        this.ethernetPayload = ethernetPayload;
        short payloadLength = ethernetPayload.getLengthInBytes();
        frameLength = 64;
        if (payloadLength >= 46) {
            frameLength = payloadLength + 18;
        }
    }


}
