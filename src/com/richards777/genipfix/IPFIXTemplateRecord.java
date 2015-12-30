package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXTemplateRecord {
    private IPFIXTemplateRecordHeader templateRecordHeader;
    private ArrayList<IPFIXFieldSpecifier> ipfixFieldSpecifiers = new ArrayList<>(4);

    public IPFIXTemplateRecord() {
        templateRecordHeader = new IPFIXTemplateRecordHeader((short) 777, (short) 4);
        ipfixFieldSpecifiers.add(new IPFIXFieldSpecifier((short)8));
        ipfixFieldSpecifiers.add(new IPFIXFieldSpecifier((short)12));
        ipfixFieldSpecifiers.add(new IPFIXFieldSpecifier((short)2));
        ipfixFieldSpecifiers.add(new IPFIXFieldSpecifier((short)1));
    }

    public short lengthInBytes() {
        short lengthInBytes = 0;
        for (IPFIXFieldSpecifier fieldSpecifier : ipfixFieldSpecifiers) {
            lengthInBytes += fieldSpecifier.lengthInBytes();
        }
        lengthInBytes += templateRecordHeader.lengthInBytes();
        return lengthInBytes;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.put(templateRecordHeader.getBuffer());
        for (IPFIXFieldSpecifier ipfixFieldSpecifier : ipfixFieldSpecifiers) {
            b.put(ipfixFieldSpecifier.getBuffer());
        }
        return b.array();
    }

}
