package org.example.object.base;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Math;

public class Transform {


    public Vector3f position = new Vector3f(0, 0, 0);
    public Vector3f rotation = new Vector3f(0, 0, 0);
    public Vector3f scale = new Vector3f(1, 1, 1);
    private float[] modelMatrix = new float[16]; // cache model matrix

    public Transform() {
        new Matrix4f().identity().get(modelMatrix);
    }

    public Transform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Vector3f(transform.rotation);
        this.scale = new Vector3f(1, 1, 1);
        new Matrix4f().identity()
                .translate(position)
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale)
                .get(this.modelMatrix);
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
        updateModelMatrix();
    }

    public void addPosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
        updateModelMatrix();
    }

    public void setRotation(float x, float y, float z) {
//        rotation.x = x % Math.PI_TIMES_2_f;
//        rotation.y = y% Math.PI_TIMES_2_f;
//        rotation.z = z% Math.PI_TIMES_2_f;
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
        updateModelMatrix();
    }

    public void addRotation(float x, float y, float z) {
//        rotation.x = (rotation.x + x)%Math.PI_TIMES_2_f;
//        rotation.y = (rotation.x + y)%Math.PI_TIMES_2_f;
//        rotation.z = (rotation.x + z)%Math.PI_TIMES_2_f;
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
        updateModelMatrix();
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
        updateModelMatrix();
    }

    public void addScale(float x, float y, float z) {
        scale.x += x;
        scale.y += y;
        scale.z += z;
        updateModelMatrix();
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    private void updateModelMatrix() {
        new Matrix4f().identity()
                .translate(position)
                .rotateY(rotation.y)
                .rotateX(rotation.x)
                .rotateZ(rotation.z)
                .scale(scale)
                .get(this.modelMatrix);
    }
}
