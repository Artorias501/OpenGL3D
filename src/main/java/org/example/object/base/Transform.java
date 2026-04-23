package org.example.object.base;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {


    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);


    private Matrix4f modelMatrix = new Matrix4f();
    private float[] modelMatrixArray = new float[16]; // cache model matrix

    public Transform() {
        modelMatrix.identity();
    }

    public Transform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Vector3f(transform.rotation);
        this.scale = new Vector3f(1, 1, 1);
        updateModelMatrix();
    }

    public Transform(Matrix4f matrix) {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f();

        // 2. 矩阵分解 → 位置、旋转、缩放
        matrix.getTranslation(position);   // 位置
        matrix.getScale(scale);           // 缩放
        matrix.getEulerAnglesXYZ(rotation);    // 旋转（欧拉角 XYZ 顺序）
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        updateModelMatrix();
    }

    public void addPosition(float x, float y, float z) {
        position.add(x, y, z);
        updateModelMatrix();
    }

    public void setRotation(float x, float y, float z) {
//        rotation.x = x % Math.PI_TIMES_2_f;
//        rotation.y = y% Math.PI_TIMES_2_f;
//        rotation.z = z% Math.PI_TIMES_2_f;
        rotation.set(x, y, z);
        updateModelMatrix();
    }

    public void addRotation(float x, float y, float z) {
//        rotation.x = (rotation.x + x)%Math.PI_TIMES_2_f;
//        rotation.y = (rotation.x + y)%Math.PI_TIMES_2_f;
//        rotation.z = (rotation.x + z)%Math.PI_TIMES_2_f;
        rotation.add(x, y, z);
        updateModelMatrix();
    }

    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
        updateModelMatrix();
    }

    public void addScale(float x, float y, float z) {
        scale.add(x, y, z);
        updateModelMatrix();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public float[] getModelMatrixArray() {
        return modelMatrixArray;
    }

    private void updateModelMatrix() {
        modelMatrix.identity()
                .translate(position)
                .rotateY(rotation.y)
                .rotateX(rotation.x)
                .rotateZ(rotation.z)
                .scale(scale)
                .get(this.modelMatrixArray);
    }
}
