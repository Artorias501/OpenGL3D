package org.example.object;

import org.example.object.base.Transform;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GLRenderObject {
    private int shaderProgram;
    private List<GLMesh> meshes = new ArrayList<GLMesh>();
    private Transform transform = new Transform();
    private int modelLocation;

    public GLRenderObject(int shaderProgram) {
        this.shaderProgram = shaderProgram;
        modelLocation = glGetUniformLocation(shaderProgram, "model");
    }

    public void addMesh(GLMesh mesh) {
        meshes.add(mesh);
    }

    public Transform getTransform() {
        return transform;
    }

    public void render() {
        glUseProgram(shaderProgram);
        glUniformMatrix4fv(modelLocation, false, transform.getModelMatrix());
        for (GLMesh mesh : meshes)
            mesh.render();
    }
}
