package org.example.util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class ByteBufferUtil {
    public static ByteBuffer toByteBuffer(List<? extends ToByteBuffer> list) {
        int itemBytes = list.get(0).byteSize();
        int itemCount = list.size();
        ByteBuffer bb = BufferUtils.createByteBuffer(itemBytes * itemCount);
        bb.order(ByteOrder.nativeOrder());

        for (ToByteBuffer item : list)
            item.putToBuffer(bb);

        return bb.flip();
    }

    public static ByteBuffer toByteBuffer(ToByteBuffer[] array) {
        int itemBytes = array[0].byteSize();
        int itemCount = array.length;

        ByteBuffer bb = BufferUtils.createByteBuffer(itemBytes * itemCount);
        bb.order(ByteOrder.nativeOrder());

        for (var item : array)
            item.putToBuffer(bb);

        return bb.flip();
    }
}
