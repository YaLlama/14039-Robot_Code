package org.firstinspires.ftc.teamcode.Controllers;

import android.renderscript.ScriptC;

public class CustomMove extends Controller{

    private double scale;
    private double scaleActual;
    private double moveX;
    private double moveY;
    private double distance;

    private double headingCorrect;

    Proportional orient = new Proportional(0.02, 0.25, 0);

    public CustomMove(double scale) {
        this.scale = scale;
        scaleActual = scale;
    }

    public double getError() {
        return 0;
    }

    public double getCorrection(double shit, double crap) {
        return 0;
    }

    public double getPowerX(double XD, double YD) {
        moveX = XD / (Math.abs(XD) + Math.abs(YD));
        distance = Math.hypot(XD, YD);
        if(distance < 25) {
            scaleActual = scale * 0.35;
        }else {
            scaleActual = scale;
        }
        return moveX * -scaleActual;
    }

    public double getPowerY(double XD, double YD) {
        moveY = YD / (Math.abs(XD) + Math.abs(YD));
        distance = Math.hypot(XD, YD);
        if(distance < 25) {
            scaleActual = scale * 0.35;
        }else {
            scaleActual = scale;
        }
        return moveY * -scaleActual;
    }

    public double getHeadingCorrect(double target, double current) {
        if(distance < 10) {
            orient.setpGain(0.01);
        }else {
            orient.setpGain(0.02);
        }
        headingCorrect = orient.getCorrection(target, current);
        return headingCorrect;
    }
}
