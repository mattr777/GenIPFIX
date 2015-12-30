package com.richards777.genipfix;

interface EthernetPayload {
    short getLengthInBytes();
    byte[] getBuffer();
}
