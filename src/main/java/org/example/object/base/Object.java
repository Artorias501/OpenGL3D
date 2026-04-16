package org.example.object.base;

import java.util.ArrayList;
import java.util.List;

public class Object {
    public Transform transform = new Transform();
    public List<Mesh> meshes = new ArrayList<>();

    public Object() {
    }

    public Object(Object object) {
        this.transform = new Transform(object.transform);
        this.meshes = new ArrayList<>();
        for (Mesh mesh : object.meshes) {
            this.meshes.add(new Mesh(mesh));
        }
    }
}
