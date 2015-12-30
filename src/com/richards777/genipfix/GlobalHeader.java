package com.richards777.genipfix;

import java.nio.ByteBuffer;

public final class GlobalHeader {

    private static final int magicNumber = 0xa1b2c3d4;; //  guint32 magic_number;   /* magic number */
    private static final short versionMajor = 2; //  guint16 version_major;  /* major version number */
    private static final short versionMinor = 4; // guint16 version_minor;  /* minor version number */
    private static final int thisZone = 0; //    gint32  thiszone;       /* GMT to local correction */
    private static final int sigFigs = 0; //    guint32 sigfigs;        /* accuracy of timestamps */
    private static final int snapLen = 65535; //    guint32 snaplen;        /* max length of captured packets, in octets */
    private static final int network = 1; //    guint32 network;        /* data link type 1 is ethernet */

    public static byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(24);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(magicNumber);
        b.putShort(versionMajor);
        b.putShort(versionMinor);
        b.putInt(thisZone);
        b.putInt(sigFigs);
        b.putInt(snapLen);
        b.putInt(network);

        return b.array();
    }
}
