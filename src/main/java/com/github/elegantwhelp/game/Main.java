package com.github.elegantwhelp.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import com.github.elegantwhelp.assets.Assets;
import com.github.elegantwhelp.gui.Gui;
import com.github.elegantwhelp.io.Timer;
import com.github.elegantwhelp.io.Window;
import com.github.elegantwhelp.render.Camera;
import com.github.elegantwhelp.render.Shader;
import com.github.elegantwhelp.render.Sprite;
import com.github.elegantwhelp.render.VertexBatcher;
import com.github.elegantwhelp.world.TileRenderer;
import com.github.elegantwhelp.world.World;

public class Main {
	public Main() {
		Window.setCallbacks();
		
		if (!glfwInit()) {
			System.err.println("GLFW Failed to initialize!");
			System.exit(1);
		}
		
		Window window = new Window();
		window.setSize(640, 480);
		window.setFullscreen(false);
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		Assets.initAsset();
		
		// float[] vertices = new float[] {
		// -1f, 1f, 0, //TOP LEFT 0
		// 1f, 1f, 0, //TOP RIGHT 1
		// 1f, -1f, 0, //BOTTOM RIGHT 2
		// -1f, -1f, 0,//BOTTOM LEFT 3
		// };
		//
		// float[] texture = new float[] {
		// 0,0,
		// 1,0,
		// 1,1,
		// 0,1,
		// };
		//
		// int[] indices = new int[] {
		// 0,1,2,
		// 2,3,0
		// };
		//
		// Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Shader batcherShader = new Shader("batcher");
		VertexBatcher batcher = new VertexBatcher();
		batcher.init();
		Sprite sprite = new Sprite(batcher, "tiles.png", 4, 4);
		
		//World world = new World("test_level", camera);
		//world.calculateView(window);
		
		Gui gui = new Gui(window);
		
		glfwSwapInterval(0);
		double frame_cap = 1.0 / 1000.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		float halfWinWidth = window.getWidth() * 0.5f;
		float halfWinHeight = window.getHeight() * 0.5f;
		
		while (!window.shouldClose()) {
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			
			time = time_2;
			
			if (window.hasResized()) {
				int winWidth = window.getWidth();
				int winHeight = window.getHeight();
				camera.setProjection(winWidth, winHeight);
				
				halfWinWidth = winWidth * 0.5f;
				halfWinHeight = winHeight * 0.5f;
				
				gui.resizeCamera(window);
				//world.calculateView(window);
				glViewport(0, 0, winWidth, winHeight);
			}
			
			while (unprocessed >= frame_cap) {
				
				unprocessed -= frame_cap;
				can_render = true;
				
				if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(window.getWindow(), true);
				}
				
				gui.update(window.getInput());
				
				//world.update((float) frame_cap, window, camera);
				
				//world.correctCamera(camera, window);
				
				window.update();
				
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if (can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				
				// shader.bind();
				// shader.setUniform("sampler", 0);
				// shader.setUniform("projection",
				// camera.getProjection().mul(target));
				// model.render();
				// tex.bind(0);
				
				//world.render(tiles, shader, camera);
				
				//gui.render();
				
				batcherShader.bind();
				batcherShader.setUniform("cam_projection", camera.getUntransformedProjection());
				batcherShader.setUniform("atlas", 0);
				
				sprite.bindTexture();
				sprite.drawSprite(-halfWinWidth,-halfWinHeight,halfWinWidth,halfWinHeight,0);
				
				batcher.draw();
				
				window.swapBuffers();
				frames++;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		batcher.destroy();
		Assets.deleteAsset();
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
