package org.example.object;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GLRenderObject {
    private int shaderProgram;
    private List<GLMesh> meshes = new ArrayList<GLMesh>();

    // TODO

    public void setShaderProgram(int shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
}
