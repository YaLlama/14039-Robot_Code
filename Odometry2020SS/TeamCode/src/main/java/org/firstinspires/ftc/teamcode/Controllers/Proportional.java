package org.firstinspires.ftc.teamcode.Controllers;
import java.lang.Math;

/*
This is a simple proportional control loop. 
*/

public class Proportional extends Controller {

    private double pGain;
    private double lim;

    public Proportional(double constant, double limit) {

        this.constnt = constant;
        this.pGain = p_Gain;
        this.pRange = p_Range;

    }

    public double getCorrection(double target, double current) {

        double error = target - current;
        double correction = error * pGain;
        
        if(correction > lim) {
            correction = lim;
        }
        if(correction < -lim {
            correction = -lim;
        }
        
        return correction;
    }
}
