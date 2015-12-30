package com.richards777.genipfix;

import java.util.HashMap;
import java.util.Map;

public class IPFIXInformationElements {
    private Map<Integer, Integer> lengthMap = new HashMap<>();
    private final int octetDeltaCount = 1;
    private final int packetDeltaCount = 2;
    private final int sourceIPv4Address = 8;
    private final int destinationIPv4Address = 12;
    private static IPFIXInformationElements instance = null;

    private IPFIXInformationElements() {
        lengthMap.put(octetDeltaCount, 8);
        lengthMap.put(packetDeltaCount, 8);
        lengthMap.put(sourceIPv4Address, 4);
        lengthMap.put(destinationIPv4Address, 4);
    }

    public short getLengthInBytes(int elementID) {
        int fieldLength = lengthMap.get(elementID);
        return (short)fieldLength;
    }
    public static synchronized IPFIXInformationElements get() {
        if (instance == null) {
            instance = new IPFIXInformationElements();
        }

        return instance;
    }
}
