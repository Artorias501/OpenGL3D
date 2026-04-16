package org.example.object;

import org.example.object.base.Object;

import static org.lwjgl.opengl.GL33.*;

public class GLRenderObject {


    // 渲染资源
    private int shaderProgram;

    // 模型数据
    private Object object;

    public GLRenderObject(Object object) {
        this.object = new Object(object);
    }

    public void setShaderProgram(int shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
}
