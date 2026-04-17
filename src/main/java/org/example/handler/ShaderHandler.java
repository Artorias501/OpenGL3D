package org.example.handler;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class ShaderHandler {
    static public int compileShaderFromFile(String fileName, int shaderType) {
        String content = FileHandler.readFile(fileName);

        int shader = glCreateShader(shaderType);
        glShaderSource(shader, content);
        glCompileShader(shader);
        IntBuffer success = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shader, GL_COMPILE_STATUS, success);
        if (success.get(0) == GL_FALSE) {
            ByteBuffer errorMsg = BufferUtils.createByteBuffer(512);
            glGetShaderInfoLog(shader, (IntBuffer) null, errorMsg);
            String error = errorMsg.toString();
            throw new RuntimeException(error);
        }

        return shader;
    }
}
