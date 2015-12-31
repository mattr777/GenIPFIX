package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class IPFIXTemplateSet extends IPFIXSet {
    private ArrayList<IPFIXTemplateRecord> templateRecords = new ArrayList<>(1);

    public short lengthInBytes() {
        return setHeader.getSetLength();
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.put(setHeader.getBuffer());
        for (IPFIXTemplateRecord templateRecord : templateRecords) {
            b.put(templateRecord.getBuffer());
        }
        return b.array();
    }

    public void addTemplateRecord(short templateID, List<String> elementNames){
        IPFIXTemplateRecord ipfixTemplateRecord  = new IPFIXTemplateRecord(templateID);
        for (String elementName : elementNames) {
            int elementID = IPFIXInformationElements.get().getElementID(elementName);
            ipfixTemplateRecord.addField(elementID);
        }
        templateRecords.add(ipfixTemplateRecord);
        setHeader.addRecordLength(ipfixTemplateRecord.lengthInBytes());

    }

    public IPFIXTemplateSet() {
        setHeader = new IPFIXSetHeader(IPFIXSetHeader.TEMPLATE_SET);
    }
}
