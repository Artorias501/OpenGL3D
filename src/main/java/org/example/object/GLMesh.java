package org.example.object;

import org.example.object.base.Mesh;
import org.example.object.base.Vertex;
import org.example.object.base.VertexAttribute;
import org.example.util.ByteBufferUtil;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GLMesh {
    private int vao;
    private int vbo;
    private int ebo;
    private int indicesCount;

    public GLMesh(Mesh mesh) {
        indicesCount = mesh.getIndices().length;

        // 启用 vao 记录属性
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // 写入顶点缓冲
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, ByteBufferUtil.toByteBuffer(mesh.getVertices()), GL_STATIC_DRAW);
        List<VertexAttribute> vas = Vertex.getAttributes();
        for (VertexAttribute va : vas) {
            glVertexAttribPointer(va.index, va.size, GL_FLOAT, false, mesh.getVertices()[0].byteSize(), va.pointer);
            glEnableVertexAttribArray(va.index);
        }

        // 写入索引缓冲
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndices(), GL_STATIC_DRAW);

        // 解绑
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getVao() {
        return vao;
    }

    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void cleanup() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
    }
}
