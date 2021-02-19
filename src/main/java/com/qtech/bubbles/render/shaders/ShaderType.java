package com.qtech.bubbles.render.shaders;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

@Getter
@ToString
@AllArgsConstructor
public enum ShaderType {
    VERTEX(GL_VERTEX_SHADER, ".vs"),
    FRAGMENT(GL_FRAGMENT_SHADER, ".fs");

    private final int type;
    private final String ext;
}
