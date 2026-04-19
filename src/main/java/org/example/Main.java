package org.example;

import org.example.handler.ShaderHandler;
import org.example.object.GLMesh;
import org.example.object.GLRenderObject;
import org.example.object.base.Mesh;
import org.example.object.base.Transform;
import org.example.object.base.Vertex;
import org.example.util.GLCheck;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.example.handler.InputHandler;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL33.*;

public class Main {
    public static void main(String[] args) {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        long window = glfwCreateWindow(800, 600, "OpenGL3D", NULL, NULL);
        GLCheck.checkNULL(window, "窗口创建失败");
        glfwMakeContextCurrent(window);
        GL.createCapabilities(); // 自动加载所有OpenGL函数
        glEnable(GL_DEPTH_TEST); // 开启深度测试
        glViewport(0, 0, 800, 600); // 设置视口大小
        GLFWFramebufferSizeCallback framebufferSizeCallback = GLFWFramebufferSizeCallback.create(
                (win, width, height) -> {
                    glViewport(0, 0, width, height);
                }
        );
        glfwSetFramebufferSizeCallback(window, framebufferSizeCallback);

        // 输出显卡信息
        System.out.println("OpenGL 版本: " + glGetString(GL_VERSION));
        System.out.println("显卡: " + glGetString(GL_RENDERER));


        System.out.println("正在编译着色器...");
        int vertexShader = 0;
        int fragmentShader = 0;
        try {
            vertexShader = ShaderHandler.compileShaderFromFile("shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
            fragmentShader = ShaderHandler.compileShaderFromFile("shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
        } catch (Exception e) {
            System.err.println("编译着色器失败:");
            System.err.println(e.getMessage());
        }

        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);

        IntBuffer success = BufferUtils.createIntBuffer(1);
        glLinkProgram(shaderProgram);
        glGetProgramiv(shaderProgram, GL_LINK_STATUS, success);
        if (success.get(0) == GL_FALSE) {
            IntBuffer infoLogLength = IntBuffer.allocate(1);
            glGetProgramiv(shaderProgram, GL_INFO_LOG_LENGTH, infoLogLength);
            ByteBuffer infoLog = ByteBuffer.allocate(infoLogLength.get(0));
            glGetProgramInfoLog(shaderProgram, infoLogLength, infoLog);
            String error = new String(infoLog.array());
            System.err.println("Shader program linking failed: " + error);
            System.exit(1);
        }
        glUseProgram(shaderProgram);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);


        // 创建测试数据
        // 一个方块
        Vertex[] vertices = new Vertex[]{
                new Vertex(
                        new Vector3f(-0.5f, -0.5f, -0.5f),
                        new Vector3f(1, 0, 0)
                ),
                new Vertex(
                        new Vector3f(-0.5f, 0.5f, -0.5f),
                        new Vector3f(0, 1, 0)
                ),
                new Vertex(
                        new Vector3f(0.5f, 0.5f, -0.5f),
                        new Vector3f(0, 0, 1)
                ),
                new Vertex(
                        new Vector3f(0.5f, -0.5f, -0.5f),
                        new Vector3f(1, 1, 0)
                ),
                new Vertex(
                        new Vector3f(-0.5f, -0.5f, 0.5f),
                        new Vector3f(1, 0, 1)
                ),
                new Vertex(
                        new Vector3f(-0.5f, 0.5f, 0.5f),
                        new Vector3f(0, 1, 1)
                ),
                new Vertex(
                        new Vector3f(0.5f, 0.5f, 0.5f),
                        new Vector3f(1, 1, 1)
                ),
                new Vertex(
                        new Vector3f(0.5f, -0.5f, 0.5f),
                        new Vector3f(0, 0, 0)
                )
        };
        int[] indices = {
                0, 1, 2, 0, 2, 3,
                0, 1, 4, 1, 4, 5,
                2, 3, 7, 2, 6, 7,
                1, 2, 6, 1, 5, 6,
                0, 3, 4, 3, 4, 7,
                4, 5, 6, 4, 6, 7
        };
        Mesh mesh = new Mesh(vertices, indices);
        GLMesh glMesh = new GLMesh(mesh);
        GLRenderObject obj = new GLRenderObject(shaderProgram);
        obj.addMesh(glMesh);

        while (!glfwWindowShouldClose(window)) {
            InputHandler.handleInput(window);

            glClearColor(0.1f, 0.15f, 0.2f, 1f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
                obj.getTransform().addRotation(0, 0.1f, 0);
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
                obj.getTransform().addRotation(0, -0.1f, 0);
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS)
                obj.getTransform().addRotation(0.1f, 0, 0);
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS)
                obj.getTransform().addRotation(-0.1f, 0, 0);

            obj.render();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        // 释放回调
        framebufferSizeCallback.free();

        glMesh.cleanup();

        glDeleteProgram(shaderProgram);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}