package com.qtech.bubbles.render.shaders;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private final int programObject;
	private final int vertexShaderObject;
	private final int fragmentShaderObject;
	private final String filename;

	public Shader(String filename) {
		this.filename = filename;

		programObject = glCreateProgram();

		vertexShaderObject = createShader(GL_VERTEX_SHADER);
		fragmentShaderObject = createShader(GL_FRAGMENT_SHADER);

//		fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
//		glShaderSource(fragmentShaderObject, readFile(filename + ".fs"));
//		glCompileShader(fragmentShaderObject);
//		if (glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS) != 1) {
//			System.err.println(glGetShaderInfoLog(fragmentShaderObject));
//			System.exit(1);
//		}
		
		glAttachShader(programObject, vertexShaderObject);
		glAttachShader(programObject, fragmentShaderObject);
		
		glBindAttribLocation(programObject, 0, "vertices");
		glBindAttribLocation(programObject, 1, "textures");
		
		glLinkProgram(programObject);
		if (glGetProgrami(programObject, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programObject));
			System.exit(1);
		}
		glValidateProgram(programObject);
		if (glGetProgrami(programObject, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programObject));
			System.exit(1);
		}
	}

	protected int createShader(int type) {
		int shaderObject = glCreateShader(type);
		glShaderSource(shaderObject, readFile(filename + (type == GL_VERTEX_SHADER ? ".vs" : ".fs")));
		glCompileShader(shaderObject);
		if (glGetShaderi(shaderObject, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(shaderObject));
			System.exit(1);
		}
		return shaderObject;
	}
	
	@Override
	protected void finalize() throws Throwable {
		glDetachShader(programObject, vertexShaderObject);
		glDetachShader(programObject, fragmentShaderObject);
		glDeleteShader(vertexShaderObject);
		glDeleteShader(fragmentShaderObject);
		glDeleteProgram(programObject);
		super.finalize();
	}
	
	public void setUniform(String uniformName, float value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform1f(location, value);
	}

	public void setUniform(String uniformName, int value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform1i(location, value);
	}

	public void setUniform(String uniformName, float[] value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform1fv(location, value);
	}

	public void setUniform(String uniformName, int[] value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform1iv(location, value);
	}

	public void setUniform(String uniformName, Vector4f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
		value.get(matrixData);
		if (location != -1) glUniformMatrix4fv(location, false, matrixData);
	}

	public void setUniform(String uniformName, Vector3f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform3f(location, value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Matrix3f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
		value.get(matrixData);
		if (location != -1) glUniformMatrix3fv(location, false, matrixData);
	}

	public void setUniform(String uniformName, Vector2f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform2f(location, value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Matrix2f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
		value.get(matrixData);
		if (location != -1) glUniformMatrix2fv(location, false, matrixData);
	}

	public void setUniform(String uniformName, Vector4i value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform4i(location, value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Vector3i value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform3i(location, value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Vector2i value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform2i(location, value.x, value.y, value.z, value.w);
	}

	public void bind() {
		glUseProgram(programObject);
	}
	
	private String readFile(String filename) {
		StringBuilder outputString = new StringBuilder();
		BufferedReader bufferedReader;
		try {
			URI filePath = getClass().getResource("/shaders/" + filename).toURI();
			bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				outputString.append(line);
				outputString.append("\n");
			}
			bufferedReader.close();
		}
		catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return outputString.toString();
	}
}
