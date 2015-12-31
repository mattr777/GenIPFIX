package com.richards777.genipfix;

import java.util.HashMap;
import java.util.Map;

public class IPFIXInformationElements {
    private Map<Integer, Integer> lengthMap = new HashMap<>();
    private Map<String, Integer> elementIDMap = new HashMap<>();
    private final int octetDeltaCount = 1;
    private final int packetDeltaCount = 2;
    private final int sourceIPv4Address = 8;
    private final int destinationIPv4Address = 12;
    private static IPFIXInformationElements instance = null;

    private IPFIXInformationElements() {
        lengthMap.put(octetDeltaCount, 8);
        elementIDMap.put("octetDeltaCount", octetDeltaCount);

        lengthMap.put(packetDeltaCount, 8);
        elementIDMap.put("packetDeltaCount", packetDeltaCount);

        lengthMap.put(sourceIPv4Address, 4);
        elementIDMap.put("sourceIPv4Address", sourceIPv4Address);

        lengthMap.put(destinationIPv4Address, 4);
        elementIDMap.put("destinationIPv4Address", destinationIPv4Address);
    }

    public short getLengthInBytes(int elementID) {
        int fieldLength = lengthMap.get(elementID);
        return (short)fieldLength;
    }

    public Integer getElementID(String elementName) {
        return elementIDMap.get(elementName);
    }

    public static synchronized IPFIXInformationElements get() {
        if (instance == null) {
            instance = new IPFIXInformationElements();
        }

        return instance;
    }
}
