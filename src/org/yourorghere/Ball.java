package org.yourorghere;

import static org.yourorghere.MotionBlur.DT;
import static org.yourorghere.MotionBlur.SC;
import static org.yourorghere.MotionBlur.SIZE;

public class Ball {

    private final Vector accelation;
    public final Vector speed;
    public final Vector location;

    public double R;

    public Ball(double R) {
        this.R = R;
        accelation = new Vector();
        speed = new Vector();
        location = new Vector();
    }

    public void integrateSpeed() {
        //добавляем с скорости ускорение с коэффициентом DT
        speed.add(accelation, DT);
        //обнуляем ускорение
        accelation.set(Vector.NULL);
    }
    
    public void integrateLocation(){
        //добавляем к положению скорость с коэффициентом DT * SC(кожффициент скорости)
        location.add(speed, DT * SC);
    }

    public void bounce() {
        //если объект пересёк стенку куба (модуль суммы положения и радиуса больше чем размер куба)
        //меняем скорость по этой компоненте на противоположную
        if (Math.abs(location.x) + R >= SIZE) {
            speed.x = -speed.x;
        }
        if (Math.abs(location.y) + R >= SIZE) {
            speed.y = -speed.y;
        }
        if (Math.abs(location.z) + R >= SIZE) {
            speed.z = -speed.z;
        }
    }

    public void kick(Vector vector) {
        accelation.add(vector);
    }
}
