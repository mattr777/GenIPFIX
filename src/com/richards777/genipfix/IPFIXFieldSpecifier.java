package com.richards777.genipfix;

import java.nio.ByteBuffer;
import java.util.ArrayList;
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

    public short getInformationElementID() {
        return (short)(informationElementID & 0xFFF);
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

    public static ByteBuffer getSimulatedValues(ArrayList<IPFIXFieldSpecifier> ipfixFieldSpecifiers, int recordLength, int scenario, int timeStamp){
        int serverIndicator = timeStamp%2;
        int nPackets = ThreadLocalRandom.current().nextInt(1, 51);
        int nOctets = ThreadLocalRandom.current().nextInt(nPackets, 1001);
        long startTimeMillis = System.currentTimeMillis() - 60000;
        long endTimeMillis = startTimeMillis + ThreadLocalRandom.current().nextInt(1000, 50000);
        byte protocol = 0;
        int protocolRand = ThreadLocalRandom.current().nextInt(1, 100);
        if (protocolRand < 11) {
            protocol = ICMP;
        } else if (protocolRand > 50) {
            protocol = TCP;
        } else {
            protocol = UDP;
        }
        int serverIPv4Address = ThreadLocalRandom.current().nextInt(0x0A000000, 0x3FFFFFFF);
        int clientIPv4Address = ThreadLocalRandom.current().nextInt(0x3FFFFFFF, 0x7FFFFFFF);
        short serverPort = (short)ThreadLocalRandom.current().nextInt(1, 48653);
        short clientPort = (short)ThreadLocalRandom.current().nextInt(49152, 65535);

        ByteBuffer b = ByteBuffer.allocate(recordLength);
        for (IPFIXFieldSpecifier ipfixFieldSpecifier : ipfixFieldSpecifiers) {
            if (ipfixFieldSpecifier.isEnterpriseField()) {
                if (ipfixFieldSpecifier.getInformationElementID() == 30) {
                    b.put((byte) (serverIndicator));
                } else {
                    for (int i = 0; i < ipfixFieldSpecifier.getFieldLength(); i++) {
                        b.put((byte) i);
                    }
                }
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("sourceIPv4Address")) {
                if (serverIndicator == 0) {
                    b.putInt(serverIPv4Address);
                } else {
                    b.putInt(clientIPv4Address);
                }
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("destinationIPv4Address")) {
                if (serverIndicator == 0) {
                    b.putInt(clientIPv4Address);
                } else {
                    b.putInt(serverIPv4Address);
                }
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("ingressInterface")) {
                b.putInt(ThreadLocalRandom.current().nextInt(1, 64));
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("packetDeltaCount")) {
                b.putLong(nPackets);
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("octetDeltaCount")) {
                b.putLong(nOctets);
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("flowStartMilliseconds")) {
                b.putLong(startTimeMillis);
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("flowEndMilliseconds")) {
                b.putLong(endTimeMillis);
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("sourceTransportPort")) {
                if (serverIndicator == 0) {
                    b.putShort(serverPort);
                } else {
                    b.putShort(clientPort);
                }
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("destinationTransportPort")) {
                if (serverIndicator == 0) {
                    b.putShort(clientPort);
                } else {
                    b.putShort(serverPort);
                }
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("protocolIdentifier")) {
                b.put(protocol);
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("vlanId")) {
                b.putShort((short) ThreadLocalRandom.current().nextInt(1, 99));
            } else if (ipfixFieldSpecifier.getInformationElementID() == IPFIXInformationElements.get().getElementID("flowId")) {
                b.putLong(endTimeMillis + clientPort);
            } else {
                for (int i = 0; i < ipfixFieldSpecifier.getFieldLength(); i++) {
                    b.put((byte) i);
                }
            }
        }
        return b;
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
