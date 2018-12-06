package org.yourorghere;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.media.opengl.GLAutoDrawable;
import static org.yourorghere.MotionBlur.angleH;
import static org.yourorghere.MotionBlur.angleV;
import static org.yourorghere.MotionBlur.len;

public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            angleH = 0;
            angleV = 90;
            len = 500;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        MotionBlur.SC = Math.max(Math.min(2d, MotionBlur.SC - e.getWheelRotation() * 0.1d), 0.0d);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            angleV -= 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            angleV += 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            angleH += 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            angleH -= 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            len += 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            len -= 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            MotionBlur.move = !MotionBlur.move;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            MotionBlur.tempBallCount = Math.min(MotionBlur.tempBallCount + 1, 100);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            MotionBlur.tempBallCount = Math.max(MotionBlur.tempBallCount - 1, 0);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            MotionBlur.AC = Math.min(MotionBlur.AC + 0.05f, 0.75f);
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            MotionBlur.AC = Math.max(MotionBlur.AC - 0.05f, 0f);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void display(GLAutoDrawable drawable) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
