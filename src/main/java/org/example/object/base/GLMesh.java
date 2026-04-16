package org.example.object.base;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class GLMesh {
    private Mesh mesh;
    private int vao;
    private int vbo;
    private int ebo;

    public GLMesh(Mesh mesh) {
        this.mesh = new Mesh(mesh);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // TODO vertices的数据类型转
//        glBufferData(GL_ARRAY_BUFFER, (ByteBuffer) mesh.vertices, GL_STATIC_DRAW);
    }
}
