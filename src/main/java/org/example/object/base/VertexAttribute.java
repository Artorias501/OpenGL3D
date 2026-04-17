package org.example.object.base;

public class VertexAttribute {
    public int index; // 对应 location = ?
    public int size; // 几维向量
    public int pointer; // 这个属性从第几个字节开始

    public VertexAttribute(int index, int size, int pointer) {
        this.index = index;
        this.size = size;
        this.pointer = pointer;
    }
}
