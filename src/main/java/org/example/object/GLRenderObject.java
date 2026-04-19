package org.example.object;

import org.example.object.base.Transform;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GLRenderObject {
    private int shaderProgram;
    public List<GLMesh> meshes = new ArrayList<>();
    public Transform transform = new Transform();
    private int transformLoc;
    public List<Integer> meshIndices = new ArrayList<>();
    public List<Transform> meshTransforms = new ArrayList<>();

    public GLRenderObject(int shaderProgram) {
        this.shaderProgram = shaderProgram;
        transformLoc = glGetUniformLocation(shaderProgram, "model");
    }

    public void render() {
        glUseProgram(shaderProgram);
        for (int i = 0; i < meshIndices.size(); i++) {
            glUniformMatrix4fv(transformLoc, false, meshTransforms.get(i).getModelMatrix());
            meshes.get(meshIndices.get(i)).render();
        }
    }
}
