package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXTemplateSet extends IPFIXSet {
    private ArrayList<IPFIXTemplateRecord> templateRecords = new ArrayList<>(1);

    public short lengthInBytes() {
        short lengthInBytes = setHeader.lengthInBytes();
        for (IPFIXTemplateRecord templateRecord : templateRecords) {
            lengthInBytes += templateRecord.lengthInBytes();
        }
        return lengthInBytes;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.put(setHeader.getBuffer());
        for (IPFIXTemplateRecord templateRecord : templateRecords) {
            b.put(templateRecord.getBuffer());
        }
        return b.array();
    }

    public IPFIXTemplateSet() {
        IPFIXTemplateRecord ipfixTemplateRecord  = new IPFIXTemplateRecord();
        templateRecords.add(ipfixTemplateRecord);
        setHeader = new IPFIXSetHeader(IPFIXSetHeader.TEMPLATE_SET, ipfixTemplateRecord.lengthInBytes());
    }
}
