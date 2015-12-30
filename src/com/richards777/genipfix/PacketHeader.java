package com.richards777.genipfix;

import java.nio.ByteBuffer;

public class PacketHeader {
    private int tsSec = 0; //        guint32 ts_sec;         /* timestamp seconds */
    private int tsuSec = 0; //    guint32 ts_usec;        /* timestamp microseconds */
    private int inclLen = 64; //    guint32 incl_len;       /* number of octets of packet saved in file */
    private int origLen = 64; //    guint32 orig_len;       /* actual length of packet */

    public PacketHeader(int tsSec, int tsuSec, int inclLen, int origLen) {
        this.tsSec = tsSec;
        this.tsuSec = tsuSec;
        this.inclLen = inclLen;
        this.origLen = origLen;
    }

    public byte[] getBuffer() {
        ByteBuffer b = ByteBuffer.allocate(16);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(tsSec);
        b.putInt(tsuSec);
        b.putInt(inclLen);
        b.putInt(origLen);

        return b.array();
    }
}
