package com.qtech.bubbles.render.shaders;

import org.joml.Vector2f;

public class CircleShader extends Shader {
    public CircleShader() {
        super("circle");
    }

    @Override
    public void bind() {
        super.bind();
    }

    public void bind(Vector2f resolution, Vector2f position, Vector2f radius) {
        bind();
        setUniform("u_resolution", resolution);
    }
}
