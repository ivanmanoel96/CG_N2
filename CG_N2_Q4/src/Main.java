
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private double ortho2D_minY, ortho2D_minX, ortho2D_maxY, ortho2D_maxX;
	
	private double raio, anglo;
	private double vertex2D_minY, vertex2D_minX, vertex2D_maxY, vertex2D_maxX;
	
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
		
		this.raio = 100;
		this.anglo = 45.0;
		this.vertex2D_minY = 0;
		this.vertex2D_minX = 0;
		this.vertex2D_maxY = 69.47d; //Coordenada Y 45º
		this.vertex2D_maxX = 70.71d; //Coordenada X 45º
	}
	
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);

		sru();
		 
		this.desenharSegmentoLinhaPreto();
		this.desenharCuboPontosPreto(this.vertex2D_minX, this.vertex2D_minY);
		this.desenharCuboPontosPreto(this.vertex2D_maxX, this.vertex2D_maxY);
		
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
	
	private void desenharSegmentoLinhaPreto() {
		this.preto();
		 gl.glLineWidth(3);
		 gl.glBegin(GL.GL_LINES);
		 	gl.glVertex2d(this.vertex2D_minX, this.vertex2D_minY);
		    gl.glVertex2d(this.vertex2D_maxX, this.vertex2D_maxY);
		 gl.glEnd();
	}
	
	private void desenharCuboPontosPreto(double origemX, double origemY) {
		this.preto();
		gl.glPointSize(5);
		gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(origemX, origemY);
		gl.glEnd();
	}
	
	private double retornarX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180));
	}
	
	private double retornarY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180));
	}
	
	private void verde() {
		gl.glColor3d(0, 1.0, 0);
	}
	
	private void vermelho() {		
		gl.glColor3d(1, 0, 0);	 	
	}
	
	private void preto() {
		gl.glColor3d(0, 0, 0);
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_Q:
				this.vertex2D_minX--;
				this.vertex2D_maxX--;
				break;
			case KeyEvent.VK_W:
				this.vertex2D_minX++;
				this.vertex2D_maxX++;
				break;
			case KeyEvent.VK_A:
				this.raio--;
				this.vertex2D_maxX = retornarX(this.anglo, this.raio) + this.vertex2D_minX;
				this.vertex2D_maxY = retornarY(this.anglo, this.raio) + this.vertex2D_minY;
				break;
			case KeyEvent.VK_S:
				this.raio++;
				this.vertex2D_maxX = retornarX(this.anglo, this.raio) + this.vertex2D_minX;
				this.vertex2D_maxY = retornarY(this.anglo, this.raio) + this.vertex2D_minY;
				break;
			case KeyEvent.VK_Z:
				this.anglo--;
				this.vertex2D_maxX = retornarX(this.anglo, this.raio) + this.vertex2D_minX;
				this.vertex2D_maxY = retornarY(this.anglo, this.raio) + this.vertex2D_minY;
				break;
			case KeyEvent.VK_X:
				this.anglo++;
				this.vertex2D_maxX = retornarX(this.anglo, this.raio) + this.vertex2D_minX;
				this.vertex2D_maxY = retornarY(this.anglo, this.raio) + this.vertex2D_minY;
				break;
		}
		glDrawable.display();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}	
}