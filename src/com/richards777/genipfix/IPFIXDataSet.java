package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXDataSet extends IPFIXSet {
    private ByteBuffer dataBuffer;
    private int nFlows;

    public int getNumberOfFlows() {
        return nFlows;
    }

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

        nFlows = (1500 - 46 - setHeader.getLengthInBytes()) / recordLength;
        dataBuffer = ByteBuffer.allocate(recordLength * nFlows + setHeader.getLengthInBytes());
        setHeader.addRecordLength((short)(recordLength * nFlows));
        dataBuffer.put(setHeader.getBuffer());
        for (int i = 0; i < nFlows; i++) {
            ByteBuffer b = IPFIXFieldSpecifier.getSimulatedValues(ipfixFieldSpecifiers, recordLength, scenario, timeStamp);
            dataBuffer.put(b.array());
        }
    }
}
