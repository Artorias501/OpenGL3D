package org.example.object;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.event.ActionEvent;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f forward = new Vector3f(0, 0, -1);
    private Vector3f center = new Vector3f(0, 0, -1);
    private Vector3f up = new Vector3f(0, 1, 0);
    private float[] viewMatrx = new float[16];

    private int width = 800;
    private int height = 600;
    private float near = 0.1f;
    private float far = 10000f;
    private float[] projectionMatrix = new float[16];

    public Camera() {
        updateViewMatrix();
        updateProjectionMatrix();
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        updateViewMatrix();
    }

    public void moveForward(float distance) {
        position.add(new Vector3f(forward).mul(distance));
        updateViewMatrix();
    }

    public void moveUp(float distance) {
        position.add(new Vector3f(up).mul(distance));
        updateViewMatrix();
    }

    public void moveRight(float distance) {
        Vector3f right = new Vector3f(forward).cross(up).normalize();
        position.add(right.mul(distance));
        updateViewMatrix();
    }

    public void lookAt(float x, float y, float z) {
        forward.set(x - position.x, y - position.y, z - position.z).normalize();
        Vector3f worldUp = new Vector3f(0, 1, 0);
        Vector3f right = new Vector3f(forward).cross(worldUp).normalize();
        up.set(worldUp.cross(right)).normalize();
        updateViewMatrix();
    }

    public void rotate(float dx, float dy) {
        Vector3f right = new Vector3f(forward).cross(up).normalize();
        forward.rotateAxis(-dx, up.x, up.y, up.z);
        right.rotateAxis(-dx, up.x, up.y, up.z);
        forward.rotateAxis(dy, right.x, right.y, right.z);
        up.rotateAxis(dy, right.x, right.y, right.z);
        updateViewMatrix();
    }

    public void setWidth(int width) {
        this.width = width;
        updateProjectionMatrix();
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
        updateProjectionMatrix();
    }

    public int getHeight() {
        return height;
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
        updateProjectionMatrix();
    }

    public float[] getViewMatrix() {
        return viewMatrx;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    private void updateViewMatrix() {
        center.set(position);
        center.add(forward);
        new Matrix4f().identity().lookAt(
                position,
                center,
                up
        ).get(viewMatrx);
    }

    private void updateProjectionMatrix() {
        new Matrix4f().identity().ortho(
                -width / 2f, width / 2f,
                -height / 2f, height / 2f,
                near, far
        ).get(projectionMatrix);
    }
}
