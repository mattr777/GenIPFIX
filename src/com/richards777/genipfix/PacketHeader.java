package com.richards777.genipfix;

public class PacketHeader {
    int tsSec; //        guint32 ts_sec;         /* timestamp seconds */
    int tsuSec; //    guint32 ts_usec;        /* timestamp microseconds */
    int inclLen; //    guint32 incl_len;       /* number of octets of packet saved in file */
    int origLen; //    guint32 orig_len;       /* actual length of packet */
}
