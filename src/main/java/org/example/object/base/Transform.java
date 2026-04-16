package org.example.object.base;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    public Vector3f position = new Vector3f(0, 0, 0);
    public Vector3f rotation = new Vector3f(0, 0, 0);
    public Vector3f scale = new Vector3f(1, 1, 1);

    public Transform() {
    }

    public Transform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Vector3f(transform.rotation);
        this.scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f()
                .translate(position) // 平移
                .rotateY((float) Math.toRadians(rotation.y)) // 旋转x3
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale); // 缩放
    }
}
