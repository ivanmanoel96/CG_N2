
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private final int TOTAL_PONTOS_CIRCULO = 72;
	
	private double ortho2D_minY, ortho2D_minX, ortho2D_maxY, ortho2D_maxX;
	
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
	}
	
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);

		sru();
		 
		this.desenharCirculoPontosAzul(0, 0, 100);
		
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
	
	private void desenharCirculoPontosAzul(double origemX, double origemY, int raio) {
		this.azul();
		gl.glPointSize(2);
		gl.glBegin(GL.GL_POINTS);
			for (int i = 0; i < TOTAL_PONTOS_CIRCULO; i++)
				gl.glVertex2d(retornarX(5 * (i+1), raio) + origemX, retornarY(5 * (i+1), raio) + origemY);
		gl.glEnd();
	}
	
	private double retornarX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180));
	}
	
	private double retornarY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180));
	}
	
	private void azul() {
		gl.glColor3d(0, 0, 1.0);		
	}
	
	private void verde() {
		gl.glColor3d(0, 1.0, 0);
	}
	
	private void vermelho() {		
		gl.glColor3d(1, 0, 0);	 	
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
//			Pan
			case KeyEvent.VK_E:
				this.ortho2D_minX++;
				this.ortho2D_maxX++;
				break;
			case KeyEvent.VK_D:
				this.ortho2D_minX--;
				this.ortho2D_maxX--;
				break;
			case KeyEvent.VK_C:
				this.ortho2D_minY--;
				this.ortho2D_maxY--;
				break;
			case KeyEvent.VK_B:
				this.ortho2D_minY++;
				this.ortho2D_maxY++;
				break;
//			Zoom
			case KeyEvent.VK_I:
				this.ortho2D_minX++;
				this.ortho2D_maxX--;
				this.ortho2D_minY++;
				this.ortho2D_maxY--;
				break;
			case KeyEvent.VK_O:
				this.ortho2D_minX--;
				this.ortho2D_maxX++;
				this.ortho2D_minY--;
				this.ortho2D_maxY++;
				break;
		}
		glDrawable.display();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}	
}