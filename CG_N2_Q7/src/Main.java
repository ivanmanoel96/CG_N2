
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private double ortho2D_minY, ortho2D_minX, ortho2D_maxY, ortho2D_maxX;
	
	private final int TOTAL_PONTOS_CIRCULO = 72;
	
	private double circloAnteriorX, circloAnteriorY, circloX, circloY; 
	private double pontoX45º, pontoY45º, pontoX135º, pontoY135º, pontoX225º, pontoY225º, pontoX315º, pontoY315º;
	
	private double anteriorX;
	private double anteriorY;
	
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
		
		this.circloAnteriorX = 200;
		this.circloAnteriorY = 200;
		this.circloX = 200;
		this.circloY = 200;
		this.pontoX45º = this.retornarX(45, 150) + 200;
		this.pontoY45º = this.retornarY(45, 150) + 200;
		this.pontoX135º = this.retornarX(135, 150) + 200;
		this.pontoY135º = this.retornarY(135, 150) + 200;
		this.pontoX225º = this.retornarX(225, 150) + 200;
		this.pontoY225º = this.retornarY(225, 150) + 200;
		this.pontoX315º = this.retornarX(315, 150) + 200;
		this.pontoY315º = this.retornarY(315, 150) + 200;
	}
	
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);

		sru();
		 
		this.desenharCirculoLinhaPreto(200, 200, 150);
		this.desenharCirculoLinhaPreto(this.circloX, this.circloY, 50);
		this.desenharCuboPontosPreto(this.circloX, this.circloY);
		this.desenharQuadradoLinhaCor();
		
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
		gl.glLineWidth(1);
		gl.glBegin(GL.GL_LINE_LOOP);
			for (int i = 0; i < TOTAL_PONTOS_CIRCULO; i++) {
				gl.glVertex2d(retornarX(5 * (i+1), raio) + origemX, retornarY(5 * (i+1), raio) + origemY);								
			}
		gl.glEnd();
	}
	
	private void desenharCuboPontosPreto(double origemX, double origemY) {
		this.preto();
		gl.glPointSize(5);
		gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(origemX, origemY);
		gl.glEnd();
	}
	
	private void desenharQuadradoLinhaCor() {
		if ((this.circloX > this.pontoX45º) || 
		    (this.circloX < this.pontoX225º) ||
		    (this.circloY > this.pontoY45º) ||
		    (this.circloY < this.pontoY225º))
			if (this.calcularRaio() >= (150 * 150))
				this.ciano();
			else
				this.amarelo();
		else
			this.roxo();
		
		this.desenharQuadradoLinha();
	}
	
	private void desenharQuadradoLinha() {
		gl.glLineWidth(1);		
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d(this.pontoX45º, this.pontoY45º);
			gl.glVertex2d(this.pontoX135º, this.pontoY135º);
			gl.glVertex2d(this.pontoX225º, this.pontoY225º);
			gl.glVertex2d(this.pontoX315º, this.pontoY315º);
		gl.glEnd();
	}
	
	private double calcularRaio() {
		double dx = this.circloAnteriorX - 200;
		double dy = this.circloAnteriorY - 200;
		return dx * dx + dy * dy;
	}
	
	private double retornarX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180));
	}
	
	private double retornarY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180));
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
	
	private void preto() {
		gl.glColor3d(0, 0, 0);
	}
	
	private void ciano() {
		gl.glColor3d(0, 1, 1);
	}
	
	private void amarelo() {
		gl.glColor3d(1, 1, 0);
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		}
		glDrawable.display();
	}
	
	public void mousePressed(MouseEvent e) {
		this.anteriorX = e.getX();
        this.anteriorY = e.getY();
	}
	    
	public void mouseReleased(MouseEvent e) {
		this.circloX = 200;
		this.circloY = 200;
		this.circloAnteriorX = 200;
		this.circloAnteriorY = 200;
		this.glDrawable.display();
	}
	
	public void mouseDragged(MouseEvent e) {
		this.circloAnteriorX += e.getX() - this.anteriorX;
		this.circloAnteriorY -= e.getY() - this.anteriorY;		
		if (this.calcularRaio() <= (150 * 150)) {
			this.circloX = this.circloAnteriorX;
			this.circloY = this.circloAnteriorY;
		}
	    
	    this.anteriorX = e.getX();
		this.anteriorY = e.getY();
		
		glDrawable.display();
	}
	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent arg0) {}
	
	public void mouseClicked(MouseEvent e) {}
}