package com.qtech.bubbles.render;

import org.lwjgl.opengl.GL12;

public class RenderSystem {
    public static void color4f(float r, float g, float b, float a) {
        GL12.glColor4f(r, g, b, a);
    }
    public static void color4i(int r, int g, int b, int a) {
        GL12.glColor4i(r, g, b, a);
    }
    public static void color3f(float r, float g, float b) {
        GL12.glColor3f(r, g, b);
    }
    public static void color3i(int r, int g, int b) {
        GL12.glColor3i(r, g, b);
    }
}
