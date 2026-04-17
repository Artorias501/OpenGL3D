package org.example.object.base;

import org.example.util.ToByteBuffer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements ToByteBuffer {
    public Vector3f position;
    public Vector3f colors = new Vector3f(1, 1, 1);
    public Vector3f normals = new Vector3f(0, 0, 0);
    public Vector2f textureCoords = new Vector2f(0, 0);

    public Vertex(Vector3f position) {
        this.position = position;
    }

    public Vertex(Vector3f position, Vector3f colors) {
        this.position = position;
        this.colors = colors;
    }

    public Vertex(Vector3f position, Vector3f colors, Vector3f normals) {
        this.position = position;
        this.colors = colors;
        this.normals = normals;
    }

    public Vertex(Vertex vertex) {
        this.position = new Vector3f(vertex.position);
        this.colors = new Vector3f(vertex.colors);
        this.normals = new Vector3f(vertex.normals);
        this.textureCoords = new Vector2f(vertex.textureCoords);
    }

    public static List<VertexAttribute> getAttributes() {
        List<VertexAttribute> attributes = new ArrayList<VertexAttribute>();
        attributes.add(new VertexAttribute(0, 3, 0));
        attributes.add(new VertexAttribute(1, 3, 3 * Float.BYTES));
        attributes.add(new VertexAttribute(2, 3, 6 * Float.BYTES));
        attributes.add(new VertexAttribute(3, 2, 9 * Float.BYTES));

        return attributes;
    }

    @Override
    public int byteSize() {
        return (3 + 3 + 3 + 2) * Float.BYTES;
    }

    @Override
    public void putToBuffer(ByteBuffer bb) {
        bb.putFloat(this.position.x);
        bb.putFloat(this.position.y);
        bb.putFloat(this.position.z);

        bb.putFloat(this.colors.x);
        bb.putFloat(this.colors.y);
        bb.putFloat(this.colors.z);

        bb.putFloat(this.normals.x);
        bb.putFloat(this.normals.y);
        bb.putFloat(this.normals.z);

        bb.putFloat(this.textureCoords.x);
        bb.putFloat(this.textureCoords.y);
    }
}
