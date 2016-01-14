package com.richards777.genipfix;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IPFIXInformationElements {
    private Map<String, Integer> lengthMap = new HashMap<>();
    private Map<String, Integer> elementIDMap = new HashMap<>();
    private Map<Integer, String> elementNameMap = new HashMap<>();
    private Map<Integer, String> typeMap = new HashMap<>();

    private static IPFIXInformationElements instance = null;

    private IPFIXInformationElements() {
        lengthMap.put("unsigned8", 1);
        lengthMap.put("unsigned16", 2);
        lengthMap.put("unsigned32", 4);
        lengthMap.put("unsigned64", 8);
        lengthMap.put("ipv4Address", 4);
        lengthMap.put("ipv6Address", 16);
        lengthMap.put("macAddress", 6);
        lengthMap.put("dateTimeSeconds", 8);
        lengthMap.put("dateTimeMilliseconds", 8);
        lengthMap.put("dateTimeMicroseconds", 8);
        lengthMap.put("dateTimeNanoseconds", 8);
        lengthMap.put("float64", 8);

        // TODO: add support for variable sized fields like octetArray, string, basicList, subTemplateList, subTemplateMultiList
        // TODO: not sure about boolean representation

        int octetDeltaCount = 1;
        elementIDMap.put("octetDeltaCount", octetDeltaCount);
        elementNameMap.put(octetDeltaCount, "octetDeltaCount");
        typeMap.put(octetDeltaCount, "unsigned64");

        int packetDeltaCount = 2;
        elementIDMap.put("packetDeltaCount", packetDeltaCount);
        elementNameMap.put(packetDeltaCount, "packetDeltaCount");
        typeMap.put(packetDeltaCount, "unsigned64");

        int deltaFlowCount = 3;
        elementIDMap.put("deltaFlowCount", deltaFlowCount);
        elementNameMap.put(deltaFlowCount, "deltaFlowCount");
        typeMap.put(deltaFlowCount, "unsigned64");

        int protocolIdentifier = 4;
        elementIDMap.put("protocolIdentifier", protocolIdentifier);
        elementNameMap.put(protocolIdentifier, "protocolIdentifier");
        typeMap.put(protocolIdentifier, "unsigned8");

        int ipClassOfService = 5;
        elementIDMap.put("ipClassOfService", ipClassOfService);
        elementNameMap.put(ipClassOfService, "ipClassOfService");
        typeMap.put(ipClassOfService, "unsigned8");

        int tcpControlBits = 6;
        elementIDMap.put("tcpControlBits", tcpControlBits);
        elementNameMap.put(tcpControlBits, "tcpControlBits");
        typeMap.put(tcpControlBits, "unsigned16");

        int sourceTransportPort = 7;
        elementIDMap.put("sourceTransportPort", sourceTransportPort);
        elementNameMap.put(sourceTransportPort, "sourceTransportPort");
        typeMap.put(sourceTransportPort, "unsigned16");

        int sourceIPv4Address = 8;
        elementIDMap.put("sourceIPv4Address", sourceIPv4Address);
        elementNameMap.put(sourceIPv4Address, "sourceIPv4Address");
        typeMap.put(sourceIPv4Address, "ipv4Address");

        int sourceIPv4PrefixLength = 9;
        elementIDMap.put("sourceIPv4PrefixLength", sourceIPv4PrefixLength);
        elementNameMap.put(sourceIPv4PrefixLength, "sourceIPv4PrefixLength");
        typeMap.put(sourceIPv4PrefixLength, "unsigned8");

        int ingressInterface = 10;
        elementIDMap.put("ingressInterface", ingressInterface);
        elementNameMap.put(ingressInterface, "ingressInterface");
        typeMap.put(ingressInterface, "unsigned32");

        int destinationTransportPort = 11;
        elementIDMap.put("destinationTransportPort", destinationTransportPort);
        elementNameMap.put(destinationTransportPort, "destinationTransportPort");
        typeMap.put(destinationTransportPort, "unsigned16");

        int destinationIPv4Address = 12;
        elementIDMap.put("destinationIPv4Address", destinationIPv4Address);
        elementNameMap.put(destinationIPv4Address, "destinationIPv4Address");
        typeMap.put(destinationIPv4Address, "ipv4Address");

        int destinationIPv4PrefixLength = 13;
        elementIDMap.put("destinationIPv4PrefixLength", destinationIPv4PrefixLength);
        elementNameMap.put(destinationIPv4PrefixLength, "destinationIPv4PrefixLength");
        typeMap.put(destinationIPv4PrefixLength, "unsigned8");

        int egressInterface = 14;
        elementIDMap.put("egressInterface", egressInterface);
        elementNameMap.put(egressInterface, "egressInterface");
        typeMap.put(egressInterface, "unsigned32");

        int ipNextHopIPv4Address = 15;
        elementIDMap.put("ipNextHopIPv4Address", ipNextHopIPv4Address);
        elementNameMap.put(ipNextHopIPv4Address, "ipNextHopIPv4Address");
        typeMap.put(ipNextHopIPv4Address, "ipv4Address");

        int bgpSourceAsNumber = 16;
        elementIDMap.put("bgpSourceAsNumber", bgpSourceAsNumber);
        elementNameMap.put(bgpSourceAsNumber, "bgpSourceAsNumber");
        typeMap.put(bgpSourceAsNumber, "unsigned32");

        int bgpDestinationAsNumber = 17;
        elementIDMap.put("bgpDestinationAsNumber", bgpDestinationAsNumber);
        elementNameMap.put(bgpDestinationAsNumber, "bgpDestinationAsNumber");
        typeMap.put(bgpDestinationAsNumber, "unsigned32");

        int bgpNextHopIPv4Address = 18;
        elementIDMap.put("bgpNextHopIPv4Address", bgpNextHopIPv4Address);
        elementNameMap.put(bgpNextHopIPv4Address, "bgpNextHopIPv4Address");
        typeMap.put(bgpNextHopIPv4Address, "ipv4Address");

        int postMCastPacketDeltaCount = 19;
        elementIDMap.put("postMCastPacketDeltaCount", postMCastPacketDeltaCount);
        elementNameMap.put(postMCastPacketDeltaCount, "postMCastPacketDeltaCount");
        typeMap.put(postMCastPacketDeltaCount, "unsigned64");

        int postMCastOctetDeltaCount = 20;
        elementIDMap.put("postMCastOctetDeltaCount", postMCastOctetDeltaCount);
        elementNameMap.put(postMCastOctetDeltaCount, "postMCastOctetDeltaCount");
        typeMap.put(postMCastOctetDeltaCount, "unsigned64");

        int flowEndSysUpTime = 21;
        elementIDMap.put("flowEndSysUpTime", flowEndSysUpTime);
        elementNameMap.put(flowEndSysUpTime, "flowEndSysUpTime");
        typeMap.put(flowEndSysUpTime, "unsigned32");

        int flowStartSysUpTime = 22;
        elementIDMap.put("flowStartSysUpTime", flowStartSysUpTime);
        elementNameMap.put(flowStartSysUpTime, "flowStartSysUpTime");
        typeMap.put(flowStartSysUpTime, "unsigned32");

        int postOctetDeltaCount = 23;
        elementIDMap.put("postOctetDeltaCount", postOctetDeltaCount);
        elementNameMap.put(postOctetDeltaCount, "postOctetDeltaCount");
        typeMap.put(postOctetDeltaCount, "unsigned64");

        int postPacketDeltaCount = 24;
        elementIDMap.put("postPacketDeltaCount", postPacketDeltaCount);
        elementNameMap.put(postPacketDeltaCount, "postPacketDeltaCount");
        typeMap.put(postPacketDeltaCount, "unsigned64");

        int minimumIpTotalLength = 25;
        elementIDMap.put("minimumIpTotalLength", minimumIpTotalLength);
        elementNameMap.put(minimumIpTotalLength, "minimumIpTotalLength");
        typeMap.put(minimumIpTotalLength, "unsigned64");

        int maximumIpTotalLength = 26;
        elementIDMap.put("maximumIpTotalLength", maximumIpTotalLength);
        elementNameMap.put(maximumIpTotalLength, "maximumIpTotalLength");
        typeMap.put(maximumIpTotalLength, "unsigned64");

        int sourceIPv6Address = 27;
        elementIDMap.put("sourceIPv6Address", sourceIPv6Address);
        elementNameMap.put(sourceIPv6Address, "sourceIPv6Address");
        typeMap.put(sourceIPv6Address, "ipv6Address");

        int destinationIPv6Address = 28;
        elementIDMap.put("destinationIPv6Address", destinationIPv6Address);
        elementNameMap.put(destinationIPv6Address, "destinationIPv6Address");
        typeMap.put(destinationIPv6Address, "ipv6Address");

        int sourceIPv6PrefixLength = 29;
        elementIDMap.put("sourceIPv6PrefixLength", sourceIPv6PrefixLength);
        elementNameMap.put(sourceIPv6PrefixLength, "sourceIPv6PrefixLength");
        typeMap.put(sourceIPv6PrefixLength, "unsigned8");

        int destinationIPv6PrefixLength = 30;
        elementIDMap.put("destinationIPv6PrefixLength", destinationIPv6PrefixLength);
        elementNameMap.put(destinationIPv6PrefixLength, "destinationIPv6PrefixLength");
        typeMap.put(destinationIPv6PrefixLength, "unsigned8");

        int flowLabelIPv6 = 31;
        elementIDMap.put("flowLabelIPv6", flowLabelIPv6);
        elementNameMap.put(flowLabelIPv6, "flowLabelIPv6");
        typeMap.put(flowLabelIPv6, "unsigned32");

        int icmpTypeCodeIPv4 = 32;
        elementIDMap.put("icmpTypeCodeIPv4", icmpTypeCodeIPv4);
        elementNameMap.put(icmpTypeCodeIPv4, "icmpTypeCodeIPv4");
        typeMap.put(icmpTypeCodeIPv4, "unsigned16");

        int igmpType = 33;
        elementIDMap.put("igmpType", igmpType);
        elementNameMap.put(igmpType, "igmpType");
        typeMap.put(igmpType, "unsigned8");

        int flowActiveTimeout = 36;
        elementIDMap.put("flowActiveTimeout", flowActiveTimeout);
        elementNameMap.put(flowActiveTimeout, "flowActiveTimeout");
        typeMap.put(flowActiveTimeout, "unsigned16");

        int flowIdleTimeout = 37;
        elementIDMap.put("flowIdleTimeout", flowIdleTimeout);
        elementNameMap.put(flowIdleTimeout, "flowIdleTimeout");
        typeMap.put(flowIdleTimeout, "unsigned16");

        int exportedOctetTotalCount = 40;
        elementIDMap.put("exportedOctetTotalCount", exportedOctetTotalCount);
        elementNameMap.put(exportedOctetTotalCount, "exportedOctetTotalCount");
        typeMap.put(exportedOctetTotalCount, "unsigned64");

        int exportedMessageTotalCount = 41;
        elementIDMap.put("exportedMessageTotalCount", exportedMessageTotalCount);
        elementNameMap.put(exportedMessageTotalCount, "exportedMessageTotalCount");
        typeMap.put(exportedMessageTotalCount, "unsigned64");

        int exportedFlowRecordTotalCount = 42;
        elementIDMap.put("exportedFlowRecordTotalCount", exportedFlowRecordTotalCount);
        elementNameMap.put(exportedFlowRecordTotalCount, "exportedFlowRecordTotalCount");
        typeMap.put(exportedFlowRecordTotalCount, "unsigned64");

        int sourceIPv4Prefix = 44;
        elementIDMap.put("sourceIPv4Prefix", sourceIPv4Prefix);
        elementNameMap.put(sourceIPv4Prefix, "sourceIPv4Prefix");
        typeMap.put(sourceIPv4Prefix, "ipv4Address");

        int destinationIPv4Prefix = 45;
        elementIDMap.put("destinationIPv4Prefix", destinationIPv4Prefix);
        elementNameMap.put(destinationIPv4Prefix, "destinationIPv4Prefix");
        typeMap.put(destinationIPv4Prefix, "ipv4Address");

        int mplsTopLabelType = 46;
        elementIDMap.put("mplsTopLabelType", mplsTopLabelType);
        elementNameMap.put(mplsTopLabelType, "mplsTopLabelType");
        typeMap.put(mplsTopLabelType, "unsigned8");

        int mplsTopLabelIPv4Address = 47;
        elementIDMap.put("mplsTopLabelIPv4Address", mplsTopLabelIPv4Address);
        elementNameMap.put(mplsTopLabelIPv4Address, "mplsTopLabelIPv4Address");
        typeMap.put(mplsTopLabelIPv4Address, "ipv4Address");

        int minimumTTL = 52;
        elementIDMap.put("minimumTTL", minimumTTL);
        elementNameMap.put(minimumTTL, "minimumTTL");
        typeMap.put(minimumTTL, "unsigned8");

        int maximumTTL = 53;
        elementIDMap.put("maximumTTL", maximumTTL);
        elementNameMap.put(maximumTTL, "maximumTTL");
        typeMap.put(maximumTTL, "unsigned8");

        int fragmentIdentification = 54;
        elementIDMap.put("fragmentIdentification", fragmentIdentification);
        elementNameMap.put(fragmentIdentification, "fragmentIdentification");
        typeMap.put(fragmentIdentification, "unsigned32");

        int postIpClassOfService = 55;
        elementIDMap.put("postIpClassOfService", postIpClassOfService);
        elementNameMap.put(postIpClassOfService, "postIpClassOfService");
        typeMap.put(postIpClassOfService, "unsigned8");

        int sourceMacAddress = 56;
        elementIDMap.put("sourceMacAddress", sourceMacAddress);
        elementNameMap.put(sourceMacAddress, "sourceMacAddress");
        typeMap.put(sourceMacAddress, "macAddress");

        int postDestinationMacAddress = 57;
        elementIDMap.put("postDestinationMacAddress", postDestinationMacAddress);
        elementNameMap.put(postDestinationMacAddress, "postDestinationMacAddress");
        typeMap.put(postDestinationMacAddress, "macAddress");

        int vlanId = 58;
        elementIDMap.put("vlanId", vlanId);
        elementNameMap.put(vlanId, "vlanId");
        typeMap.put(vlanId, "unsigned16");

        int postVlanId = 59;
        elementIDMap.put("postVlanId", postVlanId);
        elementNameMap.put(postVlanId, "postVlanId");
        typeMap.put(postVlanId, "unsigned16");

        int ipVersion = 60;
        elementIDMap.put("ipVersion", ipVersion);
        elementNameMap.put(ipVersion, "ipVersion");
        typeMap.put(ipVersion, "unsigned8");

        int flowDirection = 61;
        elementIDMap.put("flowDirection", flowDirection);
        elementNameMap.put(flowDirection, "flowDirection");
        typeMap.put(flowDirection, "unsigned8");

        int ipNextHopIPv6Address = 62;
        elementIDMap.put("ipNextHopIPv6Address", ipNextHopIPv6Address);
        elementNameMap.put(ipNextHopIPv6Address, "ipNextHopIPv6Address");
        typeMap.put(ipNextHopIPv6Address, "ipv6Address");

        int bgpNextHopIPv6Address = 63;
        elementIDMap.put("bgpNextHopIPv6Address", bgpNextHopIPv6Address);
        elementNameMap.put(bgpNextHopIPv6Address, "bgpNextHopIPv6Address");
        typeMap.put(bgpNextHopIPv6Address, "ipv6Address");

        int ipv6ExtensionHeaders = 64;
        elementIDMap.put("ipv6ExtensionHeaders", ipv6ExtensionHeaders);
        elementNameMap.put(ipv6ExtensionHeaders, "ipv6ExtensionHeaders");
        typeMap.put(ipv6ExtensionHeaders, "unsigned32");

        int flowId = 148;
        elementIDMap.put("flowId", flowId);
        elementNameMap.put(flowId, "flowId");
        typeMap.put(flowId, "unsigned64");

        int flowStartMilliseconds = 152;
        elementIDMap.put("flowStartMilliseconds", flowStartMilliseconds);
        elementNameMap.put(flowStartMilliseconds, "flowStartMilliseconds");
        typeMap.put(flowStartMilliseconds, "dateTimeMilliseconds");

        int flowEndMilliseconds = 153;
        elementIDMap.put("flowEndMilliseconds", flowEndMilliseconds);
        elementNameMap.put(flowEndMilliseconds, "flowEndMilliseconds");
        typeMap.put(flowEndMilliseconds, "dateTimeMilliseconds");
    }

    public short getLengthInBytes(int elementID) {
        String fieldType = typeMap.get(elementID);
        int fieldLength = lengthMap.get(fieldType);
        return (short)fieldLength;
    }

    public Integer getElementID(String elementName) {
        return elementIDMap.get(elementName);
    }

    public String getName(Integer elementID) {
        return elementNameMap.get(elementID);
    }

    public Set<String> getNames() {
        return elementIDMap.keySet();
    }

    public static synchronized IPFIXInformationElements get() {
        if (instance == null) {
            instance = new IPFIXInformationElements();
        }

        return instance;
    }
}
