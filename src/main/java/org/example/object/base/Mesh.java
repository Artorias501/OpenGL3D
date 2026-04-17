package org.example.object.base;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    Vertex[] vertices;
    int[] indices;

    public Mesh() {
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(Mesh mesh) {
        vertices = new Vertex[mesh.vertices.length];
        System.arraycopy(mesh.vertices, 0, vertices, 0, mesh.vertices.length);

        indices = new int[mesh.indices.length];
        System.arraycopy(mesh.indices, 0, indices, 0, mesh.indices.length);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
