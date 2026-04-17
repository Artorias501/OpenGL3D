package org.example.util;

import java.nio.ByteBuffer;

public interface ToByteBuffer {
    int byteSize();

    void putToBuffer(ByteBuffer bb);
}
