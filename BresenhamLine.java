package Bresenham;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

class ThirdGLEventListener1 implements GLEventListener {
    private GLU glu;
    @Override
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        glu = new GLU();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glViewport(0, 0, 600, 600);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 640, 0, 480);
    }
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        drawLine(gl);
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    @Override
    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }
    private void drawLine(GL gl) {
        drawPolygon(gl, 200d, 200d, 0d, 5, 120, 2);
        drawPolygon(gl, 200d, 200d, 0d, 5, 60, 4);
        drawPolygon(gl, 200d, 200d, 0d, 5, 80, 3);
        drawPolygon(gl, 200d, 200d, 0d, 5, 40, 1);
        //drawThinLine(gl,100d,0d,0d,100d);
    }

    public void drawThinLine(GL gl, double x1, double y1, double x2, double y2) {

        double x = x1;
        double y = y1;
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double s1 = sign(x2 - x1);
        double s2 = sign(y2 - y1);
        boolean interchange = false;
        if (dy > dx) {
            dx = dx + dy;
            dy = dx - dy;
            dx = dx - dy;
            interchange = true;
        } else {
            interchange = false;
        }
        double e = 2 * dy - dx;
        for (int i = 0; i < dx; i++) {
            gl.glBegin(GL.GL_POINTS);
            gl.glVertex2d(x, y);
            gl.glEnd();
            while (e > 0) {
                if (interchange) {
                    x = x + s1;
                } else {
                    y = y + s2;
                }
                e = e - 2 * dx;
            }
            if (interchange) {
                y = y + s2;
            } else {
                x = x + s1;
            }
            e = e + 2 * dy;
        }
    }

    public int sign(double a) {
        if (a > 0) {
            return 1;
        } else if (a == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public void drawThickLine(GL gl, double x1, double y1, double x2, double y2, double a, int size, int s) {
        for (double i = -size / 2; i < size / 2; i=i+0.1) {
//            drawThinLine(gl, x1 + i*Math.cos(a+Math.PI/2), y1 + i*Math.sin(a+Math.PI/2), x2 + i*Math.cos(a+Math.PI/2), y2 + i*Math.sin(a+Math.PI/2));
            drawThinLine(gl, x1 + i * Math.cos(a + ((Math.PI / 2) - (Math.PI / s))), y1 + i * Math.sin(a + ((Math.PI / 2) - (Math.PI / s))), x2 + i * Math.cos(a + ((Math.PI / 2) + (Math.PI / s))), y2 + i * Math.sin(a + ((Math.PI / 2) + (Math.PI / s))));
        }
    }

    public void drawDottedLine(GL gl, double x1, double y1, double x2, double y2, int size) {
        double len = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
        double dx = (x2 - x1) / len, dy = (y2 - y1) / len;
        double x = x1 + 0.5 * sign(dx);
        double y = y1 + 0.5 * sign(dx);
        gl.glBegin(GL.GL_POINTS);
        boolean flag = false;
        for (int i = 0; i <= len; i++) {
            if (i % size == 0) {
                flag = !flag;
            }
            if (flag == true) {
                gl.glVertex2d((int) Math.floor(x), (int) Math.floor(y));
            }
            x = x + dx;
            y = y + dy;
        }
        gl.glEnd();
    }

    public void drawPolygon(GL gl, double x, double y, double a, int sides, int size, int type) {
        double inc_angle = 2 * (Math.PI / sides);
        x = x - size / 2;
        y = y - (size / (2 * Math.tan(Math.PI / sides)));
        for (int i = 0; i < sides; i++) {
            switch (type) {
                case 1:
                    drawThinLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)));
                    System.out.println(x + "," + y);
                    break;
                case 2:
                    drawThickLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), a, 40, sides);
                    break;
                case 3:
                    drawDottedLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), 10);
                    break;
                case 4:
                    drawDottedLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), 1);
                    break;
            }
            x = (x + size * Math.cos(a));
            y = (y + size * Math.sin(a));
            a += inc_angle;
        }
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}

public class BresenhamLine {

    public static void main(String args[]) {
        GLCapabilities capabilities = new GLCapabilities();
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener1 b = new ThirdGLEventListener1();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(400, 400);
        final JFrame frame = new JFrame("Basic frame");
        frame.add(glcanvas);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
