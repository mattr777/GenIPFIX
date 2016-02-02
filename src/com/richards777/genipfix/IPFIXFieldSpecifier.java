package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public class IPFIXFieldSpecifier {
    private static final byte ICMP = 1;
    private static final byte TCP = 6;
    private static final byte UDP = 17;
    private short informationElementID = 0;
    private short fieldLength = 0;
    private int enterpriseNumber = 0;

    public IPFIXFieldSpecifier(short elementID) {
        informationElementID = elementID;
        fieldLength = IPFIXInformationElements.get().getLengthInBytes(elementID);
    }

    public IPFIXFieldSpecifier(short informationElementID, short fieldLength, int enterpriseNumber) {
        this.informationElementID = (short)(informationElementID | 0x8000);
        this.fieldLength = fieldLength;
        this.enterpriseNumber = enterpriseNumber;
    }

    public short lengthInBytes() {
        if (isEnterpriseField()) {
            return (short) 8;
        } else {
            return (short) 4;
        }
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(lengthInBytes());
        b.putShort(informationElementID);
        b.putShort(fieldLength);
        if (isEnterpriseField()) {
            b.putInt(enterpriseNumber);
        }
        return b.array();
    }

    public short getFieldLength() {
        return fieldLength;
    }

    public ByteBuffer getSimulatedValue(int scenario, int timeStamp){
        ByteBuffer b = ByteBuffer.allocate(fieldLength);
        if (isEnterpriseField()) {
            for (int i = 0; i < fieldLength; i++) {
                b.put((byte) i);
            }
            return b;
        }

        int nPackets = ThreadLocalRandom.current().nextInt(1, 51);
        int nOctets = ThreadLocalRandom.current().nextInt(nPackets, 1001);
        long startTimeMillis = System.currentTimeMillis() - 60000;
        long endTimeMillis = startTimeMillis + ThreadLocalRandom.current().nextInt(1000, 50000);
        if (informationElementID == IPFIXInformationElements.get().getElementID("protocolIdentifier")) {
            if (timeStamp % 10 == 0) {
                b.put(ICMP);
            } else if (timeStamp % 10 > 7) {
                b.put(TCP);
            } else {
                b.put(UDP);
            }
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("octetDeltaCount")) {
            b.putLong(nOctets);
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("packetDeltaCount")) {
            b.putLong(nPackets);
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("sourceIPv4Address")) {
            b.putInt(ThreadLocalRandom.current().nextInt(0x0A000000, 0x7FFFFFFF));
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("destinationIPv4Address")) {
            b.putInt(ThreadLocalRandom.current().nextInt(0x0A000000, 0x7FFFFFFF));
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("ingressInterface")) {
            b.putInt(ThreadLocalRandom.current().nextInt(1, 64));
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("flowStartMilliseconds")) {
            b.putLong(startTimeMillis);
        } else if (informationElementID == IPFIXInformationElements.get().getElementID("flowEndMilliseconds")) {
            b.putLong(endTimeMillis);
        } else {
            for (int i = 0; i < fieldLength; i++) {
                b.put((byte) i);
            }
        }
        return b;
    }

    public short getInformationElementID() {
        return informationElementID;
    }

    public String getAsJsonString(String parentIndent) {
        String indent = parentIndent + "  ";
        StringBuilder sb = new StringBuilder();

        sb.append(indent);
        sb.append("\"isEnterprise\": ");
        sb.append(isEnterpriseField());
        sb.append(",\n");

        sb.append(indent);
        sb.append("\"informationElementID\": ");
        sb.append(informationElementID & 0x7FFF);
        sb.append(",\n");

        sb.append(indent);
        sb.append("\"fieldLength\": ");
        sb.append(fieldLength);

        if (isEnterpriseField()) {
            sb.append(",\n");
            sb.append(indent);
            sb.append("\"enterpriseNumber\": ");
            sb.append(enterpriseNumber);
        }

        sb.append("\n");
        return sb.toString();
    }

    private boolean isEnterpriseField() {
        return (informationElementID & 0x8000) == 0x8000;
    }

}
