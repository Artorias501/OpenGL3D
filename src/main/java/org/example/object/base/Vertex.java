package org.example.object.base;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
    public Vector3f position;
    public Vector3f colors = new Vector3f(1, 1, 1);
    public Vector3f normals = new Vector3f(0, 0, 0);
    public Vector2f textureCoords = new Vector2f(0, 0);

    public Vertex(Vector3f position) {
        this.position = new Vector3f(position);
    }

    public Vertex(Vector3f position, Vector3f colors) {
        this.position = new Vector3f(position);
        this.colors = new Vector3f(colors);
    }

    public Vertex(Vector3f position, Vector3f colors, Vector3f normals) {
        this.position = new Vector3f(position);
        this.colors = new Vector3f(colors);
        this.normals = new Vector3f(normals);
    }

    public Vertex(Vertex vertex) {
        this.position = new Vector3f(vertex.position);
        this.colors = new Vector3f(vertex.colors);
        this.normals = new Vector3f(vertex.normals);
        this.textureCoords = new Vector2f(vertex.textureCoords);
    }

}
