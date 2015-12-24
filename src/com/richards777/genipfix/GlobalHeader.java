package com.richards777.genipfix;

public final class GlobalHeader {

    private static final String magicNumber = "a1b2c3d4"; //  guint32 magic_number;   /* magic number */  0xa1b2c3d4
    private static final String versionMajor = "0002"; //  guint16 version_major;  /* major version number */
    private static final String versionMinor = "0004"; // guint16 version_minor;  /* minor version number */
    private static final String thisZone = "00000000"; //    gint32  thiszone;       /* GMT to local correction */
    private static final String sigFigs = "00000000"; //    guint32 sigfigs;        /* accuracy of timestamps */
    private static final String snapLen = "0000FFFF"; //    guint32 snaplen;        /* max length of captured packets, in octets */
    private static final String network = "00000001"; //    guint32 network;        /* data link type 1 is ethernet */

    public static byte[] getBuffer() {
        String sb = magicNumber + versionMajor + versionMinor + thisZone + sigFigs + snapLen + network;
        return javax.xml.bind.DatatypeConverter.parseHexBinary(sb);
    }
}
