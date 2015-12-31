package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXMessage {
    private IPFIXMessageHeader ipfixMessageHeader = new IPFIXMessageHeader();
    private ArrayList<IPFIXSet> ipfixSets = new ArrayList<>(1);

    public void addSet(IPFIXSet ipfixSet) {
        ipfixSets.add(ipfixSet);
        ipfixMessageHeader.incrementLength(ipfixSet.lengthInBytes());
    }

    public int lengthInBytes() {
        int lengthInBytes = 0;
        for (IPFIXSet ipfixSet : ipfixSets) {
            lengthInBytes += ipfixSet.lengthInBytes();
        }
        lengthInBytes += ipfixMessageHeader.lengthInBytes();
        return lengthInBytes;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.put(ipfixMessageHeader.getBuffer());
        for (IPFIXSet ipfixSet : ipfixSets) {
            b.put(ipfixSet.getBuffer());
        }
        return b.array();
    }

}
