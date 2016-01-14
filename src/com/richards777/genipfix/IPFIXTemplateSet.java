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

    public void addTemplateRecord(short templateID, List<IPFIXFieldSpecifier> ipfixFields){
        IPFIXTemplateRecord ipfixTemplateRecord  = new IPFIXTemplateRecord(templateID);
        ipfixFields.forEach(ipfixTemplateRecord::addField);
        templateRecords.add(ipfixTemplateRecord);
        setHeader.addRecordLength(ipfixTemplateRecord.lengthInBytes());
    }

    public IPFIXTemplateSet() {
        setHeader = new IPFIXSetHeader(IPFIXSetHeader.TEMPLATE_SET);
    }

    public IPFIXTemplateRecord getTemplateRecord() {
        return templateRecords.get(0);
    }

    public String getAsJsonString() {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        int recordNum = 0;
        for (IPFIXTemplateRecord templateRecord : templateRecords) {
            recordNum++;
            String subIndent = indent + "  ";
            sb.append(subIndent);
            sb.append("{\n");
            sb.append(templateRecord.getAsJsonString(subIndent));
            sb.append("\n");
            sb.append(subIndent);
            if (recordNum < templateRecords.size()) {
                sb.append("},\n");
            } else {
                sb.append("}\n");
            }
        }
        sb.append(indent);
        sb.append("]\n");
        return sb.toString();
    }
}
