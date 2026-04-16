package org.example.handler;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
    static public void handleInput(long window) {
        handleESCAPE(window);
    }

    static private void handleESCAPE(long window) {
        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);
    }
}
