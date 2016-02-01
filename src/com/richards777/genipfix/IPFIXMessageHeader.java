package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class IPFIXMessageHeader {
    private final short versionNumber = 10;  // IPFIX is version 10 of netflow
    private short lengthInBytes = 16;  //Total length of the IPFIX Message, measured in octets, including Message Header and Set(s).
    private int exportTime = 1451499299;  //    Time at which the IPFIX Message Header leaves the Exporter,
    //    expressed in seconds since the UNIX epoch of 1 January 1970 at
//    00:00 UTC, encoded as an unsigned 32-bit integer.
    private int sequenceNumber;   //    Incremental sequence counter modulo 2^32 of all IPFIX Data Records
    //    sent in the current stream from the current Observation Domain by
//    the Exporting Process.  Each SCTP Stream counts sequence numbers
//    separately, while all messages in a TCP connection or UDP session
//    are considered to be part of the same stream.  This value can be
//    used by the Collecting Process to identify whether any IPFIX Data
//    Records have been missed.  Template and Options Template Records
//    do not increase the Sequence Number.
    private int observationDomainID = 0x99999999; //    A 32-bit identifier of the Observation Domain that is locally
//    unique to the Exporting Process.  The Exporting Process uses the
//    Observation Domain ID to uniquely identify to the Collecting
//    Process the Observation Domain that metered the Flows.  It is
//    RECOMMENDED that this identifier also be unique per IPFIX Device.
//    Collecting Processes SHOULD use the Transport Session and the
//    Observation Domain ID field to separate different export streams
//    originating from the same Exporter.  The Observation Domain ID
//    SHOULD be 0 when no specific Observation Domain ID is relevant for
//    the entire IPFIX Message, for example, when exporting the
//    Exporting Process Statistics, or in the case of a hierarchy of
//    Collectors when aggregated Data Records are exported.

    public IPFIXMessageHeader(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void incrementLength(short setLength) {
        lengthInBytes += setLength;
    }

    public int getLengthInBytes() {
        return 16;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(getLengthInBytes());
        b.putShort(versionNumber);
        b.putShort(lengthInBytes);
        b.putInt(exportTime);
        b.putInt(sequenceNumber);
        b.putInt(observationDomainID);

        return b.array();
    }

}
