package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXTemplateRecord {
    private IPFIXTemplateRecordHeader templateRecordHeader;
    private ArrayList<IPFIXFieldSpecifier> ipfixFieldSpecifiers = new ArrayList<>(4);

    public IPFIXTemplateRecord(short templateID) {
        templateRecordHeader = new IPFIXTemplateRecordHeader(templateID);
    }

    public void addField(int elementID) {
        ipfixFieldSpecifiers.add(new IPFIXFieldSpecifier((short)elementID));
        templateRecordHeader.incrementFieldCount();
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

    public short getTemplateID() {
        return templateRecordHeader.getTemplateID();
    }

    public ArrayList<IPFIXFieldSpecifier> getFieldSpecifiers() {
        return ipfixFieldSpecifiers;
    }

}
