
import java.awt.Point;
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
	
	private Poliedro poliedro;
	private Point pontoSelecionado;	
	private final int TOTAL_PONTOS_SPLINE = 100;
	
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
		
		this.poliedro = new Poliedro();
		this.poliedro.ponto1.x = -100;
		this.poliedro.ponto1.y = -100;		
		this.poliedro.ponto2.x = -100;
		this.poliedro.ponto2.y = 100;		
		this.poliedro.ponto3.x = 100;
		this.poliedro.ponto3.y = 100;		
		this.poliedro.ponto4.x = 100;
		this.poliedro.ponto4.y = -100;
		this.pontoSelecionado = new Point();
		this.pontoSelecionado = this.poliedro.ponto1;
	}
	
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);

		sru();
		 
		this.desenharPoliedroLinhaCiano();
		this.desenharCuboPontosVermelho(this.pontoSelecionado.x, this.pontoSelecionado.y);
		this.desenharSplineLinhaAmarelo();
		
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
	
	private void desenharPoliedroLinhaCiano() {
		this.ciano();
		gl.glLineWidth(3);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2d(this.poliedro.ponto1.x, this.poliedro.ponto1.y);
			gl.glVertex2d(this.poliedro.ponto2.x, this.poliedro.ponto2.y);
			
			gl.glVertex2d(this.poliedro.ponto2.x, this.poliedro.ponto2.y);
			gl.glVertex2d(this.poliedro.ponto3.x, this.poliedro.ponto3.y);
			
			gl.glVertex2d(this.poliedro.ponto3.x, this.poliedro.ponto3.y);
			gl.glVertex2d(this.poliedro.ponto4.x, this.poliedro.ponto4.y);
		gl.glEnd();
	}
	
	private void desenharSplineLinhaAmarelo() {
		this.amarelo();
		gl.glLineWidth(3);
		gl.glBegin(GL.GL_LINES);
			Point pontoOrigem = this.poliedro.ponto1;
			for (int t = 1; t <= TOTAL_PONTOS_SPLINE; t++) {
				Point p1p2 = this.interpolarSpline(this.poliedro.ponto1, this.poliedro.ponto2, t);
				Point p2p3 = this.interpolarSpline(this.poliedro.ponto2, this.poliedro.ponto3, t);
				Point p3p4 = this.interpolarSpline(this.poliedro.ponto3, this.poliedro.ponto4, t);
				
				Point p1p2p3 = this.interpolarSpline(p1p2, p2p3, t);
				Point p2p3p4 = this.interpolarSpline(p2p3, p3p4, t);
				
				Point p1p2p3p4 = this.interpolarSpline(p1p2p3, p2p3p4, t);
				
				gl.glVertex2d(pontoOrigem.x, pontoOrigem.y);
				gl.glVertex2d(p1p2p3p4.x, p1p2p3p4.y);
				
				pontoOrigem = p1p2p3p4;
			}
		gl.glEnd();
	}
	
	private Point interpolarSpline(Point pontoOrigem, Point pontoDestino, int t) {
		Point pontoInterpolado = new Point();
		pontoInterpolado.x = pontoOrigem.x + (pontoDestino.x - pontoOrigem.x) * t/TOTAL_PONTOS_SPLINE;
		pontoInterpolado.y = pontoOrigem.y + (pontoDestino.y - pontoOrigem.y) * t/TOTAL_PONTOS_SPLINE;		
		return pontoInterpolado;
	}
	
	private void desenharCuboPontosVermelho(double origemX, double origemY) {
		this.vermelho();
		gl.glPointSize(8);
		gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(origemX, origemY);
		gl.glEnd();
	}
	
	private void verde() {
		gl.glColor3d(0, 1.0, 0);
	}
	
	private void vermelho() {		
		gl.glColor3d(1, 0, 0);
	}
	
	private void ciano() {
		gl.glColor3d(0, 1, 1);
	}
	
	private void amarelo() {
		gl.glColor3d(1, 1, 0);
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_1:
				this.pontoSelecionado = this.poliedro.ponto1;
				break;
			case KeyEvent.VK_2:
				this.pontoSelecionado = this.poliedro.ponto2;
				break;
			case KeyEvent.VK_3:
				this.pontoSelecionado = this.poliedro.ponto3;
				break;
			case KeyEvent.VK_4:
				this.pontoSelecionado = this.poliedro.ponto4;
				break;
		}
		glDrawable.display();
	}
	
	public void mousePressed(MouseEvent e) {
		this.anteriorX = e.getX();
        this.anteriorY = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		this.pontoSelecionado.x += e.getX() - this.anteriorX;
		this.pontoSelecionado.y -= e.getY() - this.anteriorY;
		
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
	
	public void mouseReleased(MouseEvent arg0) {}		
}