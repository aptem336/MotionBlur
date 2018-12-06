package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class MotionBlur implements GLEventListener {

    private static GL gl;
    private static GLU glu;
    private static GLUT glut;

    private static long time;
    private static long lc_time;
    private static int frames = 60;
    private static int fps;
    public static double PC;

    public static float AC = 0.6f;
    public static double SC = 1.0d;

    private static Listener listener;
    private static GLCanvas canvas;
    public static Animator animator;

    public static double len = 500d;
    public static double angleV = 90d, angleH = 0d;
    private static double xView, zView, yView;

    private static final ArrayList<Ball> BALLS = new ArrayList<>();
    public static double DT = 1d / 45d;
    public static final double SIZE = 120d;

    private static int ballCount = 0;
    public static int tempBallCount = 10;

    public static boolean move = true;

    public static void main(String[] args) {
        //������ ����
        Frame frame = new Frame("MotionBlur!");
        frame.setType(Window.Type.UTILITY);
        //�������� ������� ������
        int size = Toolkit.getDefaultToolkit().getScreenSize().height - 30;
        frame.setSize(size, size);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //������ ����� - ����� ���������
        canvas = new GLCanvas();
        //������ � ��������� ����������
        listener = new Listener();
        canvas.addKeyListener(listener);
        canvas.addMouseListener(listener);
        canvas.addMouseMotionListener(listener);
        canvas.addMouseWheelListener(listener);
        canvas.addGLEventListener(new MotionBlur());
        canvas.setBounds(0, 0, frame.getWidth(), frame.getHeight() - 30);
        //������ ����������� �������� ��������� ��������
        animator = new Animator(canvas);

        frame.add(canvas);
        //��������� ��������� ����
        frame.addWindowListener(new WindowAdapter() {
            @Override
            //�������������� �������� �������� ����
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    //������������� ��������
                    animator.stop();
                    //������� �� �������� 0 (��� ������)
                    System.exit(0);
                }).start();
            }
        });
        //��������� ��������
        animator.start();
        //��������� ������������ ���������� �����
        checkCount();
    }

    private static void checkCount() {
        //���� ���������� ����� ������ ����������� �������������
        //����������� ������� ���������� �����
        for (; ballCount < tempBallCount; ballCount++) {
            //��������� ��� � ��������� ��������
            BALLS.add(new Ball(Math.random() * 5d + 5d));
            //��������� �������� � ����
            BALLS.get(BALLS.size() - 1).kick(new Vector(Math.random() * 10000d - 5000d, Math.random() * 10000d - 5000d, Math.random() * 10000d - 5000d));
        }
        //���� ���������� ����� ������ ����������� �������������
        //��������� ������� ���������� �����
        for (; tempBallCount < ballCount; ballCount--) {
            //������� ��������� ���
            BALLS.remove(BALLS.size() - 1);
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();

        //����������� ����
        //�������� ��������� �����
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        //������������� ������� �����
        gl.glLightiv(GL.GL_LIGHT0, GL.GL_POSITION, new int[]{1, 1, 1, 0}, 0);
        //�������� ���������� ���������� ���������
        gl.glEnable(GL.GL_COLOR_MATERIAL);

        //�������� ����� �����, ����������, ���� �������
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_ALPHA_TEST);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL.GL_DEPTH_TEST);
        //������� ����� ���� ����� ������
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 100000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private static void calcCam() {
        //��������� ���������� ����� ������������ � �������������� ����
        xView = len * Math.sin(Math.toRadians(angleV)) * Math.cos(Math.toRadians(angleH));
        zView = len * Math.sin(Math.toRadians(angleV)) * Math.sin(Math.toRadians(angleH));
        yView = len * Math.cos(Math.toRadians(angleV));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslated(0d, 0d, 0d);

        //�������� ����� ���������� ���������� ��������� ��� ������
        calcCam();
        //���������� ������ � ����������� � �����������
        glu.gluLookAt(xView, yView, zView, 0d, 0d, 0d, 0d, 0.5d, 0d);

        //���� ��� �������� fps
        //������� �����
        time = System.currentTimeMillis();
        //���� ������ ������ �������
        if (time - lc_time >= 1000) {
            //�������� fps ����� ����������� ������
            fps = frames;
            //��������� ����� ��� ���������� �������
            lc_time = time;
            //�������� ���������� �������
            frames = 0;
        }
        //����������� ���������� ������
        frames++;

        //�������� ����� ������������� ��������� ���-�� ����� � ��������� �������������
        checkCount();

        //���� �������� �� �� �����
        if (move) {
            //��� ������� ����
            BALLS.forEach((ball) -> {
                //����������� �������� � ���������
                ball.integrateSpeed();
                ball.integrateLocation();
                //��������� �������� �� �������
                ball.bounce();
            });
        }
        //��������� � ����� ����� ����������� � ����������� ����� �������
        gl.glAccum(GL.GL_RETURN, AC);
        //��� ������� �����
        BALLS.forEach((ball) -> {
            buildSphere(ball.location.x, ball.location.y, ball.location.z, ball.R, new Color(0xffffffff));
        });
        //��������� � ������ ����������� ������������
        gl.glAccum(GL.GL_LOAD, AC);
        //������ ��� - �.�. �� ����������
        buildCube(0d, 0d, 0d, SIZE * 2d, new Color(0x20ffffff, true));

        gl.glFlush();
        //�������� ����� ������ ��������
        drawText(drawable);
    }

    private static void drawText(GLAutoDrawable drawable) {
        gl.glColor4f(1f, 1f, 1f, 0.6f);
        gl.glWindowPos2i(10, drawable.getHeight() - 40);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, fps + "   fps");

        gl.glWindowPos2i(10, drawable.getHeight() - 60);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, AC + "   accum coefficient");

        gl.glWindowPos2i(10, drawable.getHeight() - 80);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, SC + "   speed coef");

        gl.glWindowPos2i(10, drawable.getHeight() - 100);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, ballCount + "   count of balls");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 40);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "W, S, A, D, E, Q - camera");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 60);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "Esc - camera reset");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 80);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "Shift, Ctrl - +/- accumulation coefficient");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 100);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "MouseWheel - +/- speed coefficient");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 120);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "Space - pause");

        gl.glWindowPos2i(drawable.getWidth() - 220, drawable.getHeight() - 140);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "UP, DOWN - add/remove ball");
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private static final int SLICES = 20, STACKS = 20;

    private static void buildSphere(double x, double y, double z, double size, Color color) {
        gl.glPushMatrix();
        //��������� ����� � ����� (x, y, z)
        gl.glTranslated(x, y, z);
        //������������� ���� � float
        gl.glColor4d(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, color.getAlpha() / 255d);
        glut.glutSolidSphere(size, SLICES, STACKS);
        gl.glPopMatrix();
    }

    private static void buildCube(double x, double y, double z, double size, Color color) {
        gl.glPushMatrix();
        //��������� ��� � ����� (x, y, z)
        gl.glTranslated(x, y, z);
        //������������� ���� � float
        gl.glColor4d(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, color.getAlpha() / 255d);
        glut.glutSolidCube((float) size);
        gl.glPopMatrix();
    }
}
