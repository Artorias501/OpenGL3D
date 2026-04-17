package org.example.util;

import static org.lwjgl.opengl.GL11.*;

public class GLCheck {
    public static void checkNULL(Object obj) {
        checkNULL(obj, "未指定信息的错误");
    }

    public static void checkNULL(Object obj, String msg) {
        if (obj == null) {
            System.out.println(msg);
            System.exit(1);
        }
    }

    // ==================== 核心：OpenGL 错误检查 ====================
    private static void checkGLError() {
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            String errStr = switch (error) {
                case GL_INVALID_ENUM -> "GL_INVALID_ENUM";
                case GL_INVALID_VALUE -> "GL_INVALID_VALUE";
                case GL_INVALID_OPERATION -> "GL_INVALID_OPERATION";
                case GL_STACK_OVERFLOW -> "GL_STACK_OVERFLOW";
                case GL_STACK_UNDERFLOW -> "GL_STACK_UNDERFLOW";
                case GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY";
                default -> "未知错误:" + error;
            };
            fatal("OpenGL 错误 : " + errStr);
        }
    }

    // ==================== 崩溃 ====================
    private static void fatal(String msg) {
        System.err.println("\n[Error] " + msg);
        System.exit(1);
    }
}
