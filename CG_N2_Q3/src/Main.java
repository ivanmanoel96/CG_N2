
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener {
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
		 
		this.desenharCirculoLinhaPreto(100, 100, 100);
		this.desenharCirculoLinhaPreto(-100, 100, 100);
		this.desenharCirculoLinhaPreto(0, -100, 100);
		this.desenharTrianguloLinhaCiano();
		
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
	
	private void desenharCirculoLinhaPreto(double origemX, double origemY, int raio) {
		this.preto();
		gl.glLineWidth(2);
		gl.glBegin(GL.GL_LINE_LOOP);
			for (int i = 0; i < TOTAL_PONTOS_CIRCULO; i++)
				gl.glVertex2d(retornarX(5 * (i+1), raio) + origemX, retornarY(5 * (i+1), raio) + origemY);
		gl.glEnd();
	}
	
	private void desenharTrianguloLinhaCiano() {
		this.ciano();
		gl.glLineWidth(1);
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d(100, 100);
			gl.glVertex2d(0, -100);
			gl.glVertex2d(-100, 100);
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
	
	private void ciano() {
		gl.glColor3d(0, 1, 1);
	}
	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}
}