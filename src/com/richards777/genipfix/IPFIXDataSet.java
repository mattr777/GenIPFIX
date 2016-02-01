package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXDataSet extends IPFIXSet {
    private ByteBuffer dataBuffer;

    public short lengthInBytes() {
        return setHeader.getSetLength();
    }

    public byte[] getBuffer() {
        return dataBuffer.array();
    }

    public IPFIXDataSet(IPFIXTemplateRecord templateRecord, int scenario, int timeStamp) {
        setHeader = new IPFIXSetHeader(templateRecord.getTemplateID());
        ArrayList<IPFIXFieldSpecifier> ipfixFieldSpecifiers = templateRecord.getFieldSpecifiers();
        int recordLength = 0;
        for (IPFIXFieldSpecifier ipfixFieldSpecifier : ipfixFieldSpecifiers) {
            recordLength += ipfixFieldSpecifier.getFieldLength();
        }

        dataBuffer = ByteBuffer.allocate(recordLength + setHeader.getLengthInBytes());
        setHeader.addRecordLength((short)recordLength);
        dataBuffer.put(setHeader.getBuffer());
        for (IPFIXFieldSpecifier ipfixFieldSpecifier : ipfixFieldSpecifiers) {
            ByteBuffer b = ipfixFieldSpecifier.getSimulatedValue(scenario, timeStamp);
            dataBuffer.put(b.array());
        }

    }
}
