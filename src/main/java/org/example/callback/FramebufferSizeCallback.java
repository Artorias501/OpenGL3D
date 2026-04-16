package org.example.callback;

@FunctionalInterface
public interface FramebufferSizeCallback {
    void framebufferSizeCallback(long window, int width, int height);
}
