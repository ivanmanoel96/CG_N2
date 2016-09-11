
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private double ortho2D_minY, ortho2D_minX, ortho2D_maxY, ortho2D_maxX;
	
	private int tipoPrimitivaAtual;
	
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		gl.glClearColor(1, 1, 1, 1);
		
		this.ortho2D_minY = -400;
		this.ortho2D_minX = -400;
		this.ortho2D_maxY = 400;
		this.ortho2D_maxX = 400;
		
		this.tipoPrimitivaAtual = 0;
	}
	
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);

		sru();
		 
		this.desenharPrimitivas();
		
		gl.glFlush();
	}
	
	public void sru() {
		// Eixo x
		this.vermelho();
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2d(-200, 0 );
			gl.glVertex2d(200, 0 );
		gl.glEnd();
		// Eixo y
		this.verde();
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2d(0, -200);
			gl.glVertex2d(0, 200);
		gl.glEnd();
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}
	
	private void desenharPrimitivas() {
		gl.glPointSize(5);
		gl.glLineWidth(5);    		
		gl.glBegin(this.tipoPrimitivaAtual);
	    	switch (this.tipoPrimitivaAtual) {
		    	case GL.GL_POINTS:
		    		this.desenharCoordenadaAzul();
			    	this.desenharCoordenadaRoxo();
				 	this.desenharCoordenadaVerde();
				 	this.desenharCoordenadaVermelho();
				 	break;
				case GL.GL_LINES:
					this.desenharCoordenadaAzul();
			    	this.desenharCoordenadaRoxo();
				 	this.desenharCoordenadaVerde();
				 	this.desenharCoordenadaVermelho();
					break;				
				case GL.GL_LINE_LOOP:
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
				 	this.desenharCoordenadaVermelho();
			    	this.desenharCoordenadaRoxo();
				 	break;
				case GL.GL_LINE_STRIP:
					this.desenharCoordenadaRoxo();
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
				 	this.desenharCoordenadaVermelho();
					break;
				case GL.GL_TRIANGLES:
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
				 	this.desenharCoordenadaVermelho();
					break;
				case GL.GL_TRIANGLE_STRIP:
					this.desenharCoordenadaRoxo();
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVermelho();
					this.desenharCoordenadaVerde();					
					break;
				case GL.GL_TRIANGLE_FAN:
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVermelho();
					this.desenharCoordenadaVerde();
					this.desenharCoordenadaRoxo();
					break;
				case GL.GL_QUADS:
					this.desenharCoordenadaRoxo();
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
					this.desenharCoordenadaVermelho();
					break;
				case GL.GL_QUAD_STRIP:
					this.desenharCoordenadaRoxo();
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
					this.desenharCoordenadaVermelho();
					break;
				case GL.GL_POLYGON:
					this.desenharCoordenadaAzul();
					this.desenharCoordenadaVerde();
					this.desenharCoordenadaVermelho();
					this.desenharCoordenadaRoxo();
					this.tipoPrimitivaAtual = -1;
					break;
			}	    
    	gl.glEnd();
 	}
	
	private void desenharCoordenadaAzul() {
		this.azul();
		gl.glVertex2d(-200, 200);
	}
	
	private void desenharCoordenadaRoxo() {
		this.roxo();
	 	gl.glVertex2d(-200, -200);	 	
	}
	
	private void desenharCoordenadaVerde() {
		this.verde();
		gl.glVertex2d(200, 200);
	}
	
	private void desenharCoordenadaVermelho() {
		this.vermelho();
		gl.glVertex2d(200, -200);
	}
	
	private void azul() {
		gl.glColor3d(0, 0, 1.0);		
	}
	
	private void roxo() {		
		gl.glColor3d(1.0, 0, 1.0);	 	
	}
	
	private void verde() {
		gl.glColor3d(0, 1.0, 0);
	}
	
	private void vermelho() {		
		gl.glColor3d(1, 0, 0);	 	
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				this.tipoPrimitivaAtual++;
				break;
		}
		glDrawable.display();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}	
}