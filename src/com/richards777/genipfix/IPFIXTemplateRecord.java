package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IPFIXTemplateRecord {
    private IPFIXTemplateRecordHeader templateRecordHeader;
    private ArrayList<IPFIXFieldSpecifier> ipfixFieldSpecifiers = new ArrayList<>(4);

    public IPFIXTemplateRecord(short templateID) {
        templateRecordHeader = new IPFIXTemplateRecordHeader(templateID);
    }

    public void addField(IPFIXFieldSpecifier ipfixFieldSpecifier) {
        ipfixFieldSpecifiers.add(ipfixFieldSpecifier);
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

    public String getAsJsonString(String parentIndent) {
        String indent = parentIndent + "    ";
        int nFields = templateRecordHeader.getFieldCount();
        int fieldNum = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(templateRecordHeader.getAsJsonString());
        sb.append(indent);
        sb.append("[\n");
        for (IPFIXFieldSpecifier ipfixFieldSpecifier : ipfixFieldSpecifiers) {
            fieldNum++;
            String subIndent = indent + "    ";
            sb.append(subIndent);
            sb.append("{\n");
            sb.append(ipfixFieldSpecifier.getAsJsonString(subIndent));
            sb.append(subIndent);
            if (fieldNum < nFields) {
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
