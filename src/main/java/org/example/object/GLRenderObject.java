package org.example.object;

import org.example.object.base.Transform;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GLRenderObject {
    private int shaderProgram;
    public List<GLMesh> meshes = new ArrayList<>();
    public Transform transform = new Transform();
    private int transformLoc;
    private float[] transformCache = new float[16];

    public List<MeshDrawCommand> meshDrawCommands = new ArrayList<>();

    public GLRenderObject(int shaderProgram) {
        this.shaderProgram = shaderProgram;
        transformLoc = glGetUniformLocation(shaderProgram, "model");
    }

    public void render() {
        glUseProgram(shaderProgram);
        for (MeshDrawCommand meshDrawCommand : meshDrawCommands) {
            Matrix4f finalTransform = new Matrix4f();
            transform.getModelMatrix().mul(meshDrawCommand.meshTransform.getModelMatrix(), finalTransform);
            finalTransform.get(transformCache);

            glUniformMatrix4fv(transformLoc, false, transformCache);
            meshes.get(meshDrawCommand.meshIndex).render();
        }
    }

    public void cleanup() {
        for (GLMesh mesh : meshes)
            mesh.cleanup();

        meshDrawCommands.clear();
    }
}
