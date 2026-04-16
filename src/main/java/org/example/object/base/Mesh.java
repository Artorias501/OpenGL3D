package org.example.object.base;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    public List<Vertex> vertices = new ArrayList<>();
    public List<Integer> indices = new ArrayList<>();

    public Mesh() {
    }

    public Mesh(Mesh mesh) {
        vertices = new ArrayList<>();
        for (Vertex vertex : mesh.vertices) {
            vertices.add(new Vertex(vertex));
        }

        indices = new ArrayList<>(mesh.indices);
    }
}
