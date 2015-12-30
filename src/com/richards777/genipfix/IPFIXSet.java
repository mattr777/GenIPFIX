package com.richards777.genipfix;

abstract class IPFIXSet {
    protected IPFIXSetHeader setHeader;

    abstract short lengthInBytes();
    abstract byte[] getBuffer();

}
