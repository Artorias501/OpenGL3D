package org.example;

import org.example.handler.ShaderHandler;
import org.example.util.GLCheck;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.example.handler.InputHandler;
import org.lwjgl.opengl.GL20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL33.*;

public class Main {
    public static void main(String[] args) {
        glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        long window = glfwCreateWindow(800, 600, "OpenGL3D", NULL, NULL);
        GLCheck.checkNULL(window, "窗口创建失败");
        glfwMakeContextCurrent(window);
        GL.createCapabilities(); // 自动加载所有OpenGL函数
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

        // temp
        // 两个三角形
        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
        float[] vertices2 = {
                -0.2f, -0.2f, 0.0f,
                0.2f, -0.2f, 0.0f,
                0.0f, 0.2f, 0.0f
        };

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vao2 = glGenVertexArrays();
        glBindVertexArray(vao2);

        int vbo2 = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo2);
        glBufferData(GL_ARRAY_BUFFER, vertices2, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // 一个矩形，采用EBO绘制
        float[] rectVertices = {
                -0.5f, 0.8f, 0.0f,   // 右上角
                -0.5f, 0.5f, 0.0f,  // 右下角
                -0.8f, 0.5f, 0.0f, // 左下角
                -0.8f, 0.8f, 0.0f   // 左上角
        };
        int[] rectIndices = {
                0, 1, 2,
                0, 2, 3
        };
        int rectVao = glGenVertexArrays();
        glBindVertexArray(rectVao);

        int rectVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, rectVbo);
        glBufferData(GL_ARRAY_BUFFER, rectVertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        int rectEbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rectEbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, rectIndices, GL_STATIC_DRAW);

        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);


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


        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        int cnt = 0;
        int curVao = vao;
        while (!glfwWindowShouldClose(window)) {
            InputHandler.handleInput(window);

            glClearColor(0.1f, 0.15f, 0.2f, 1f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        // 释放回调
        framebufferSizeCallback.free();

        glDeleteProgram(shaderProgram);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}