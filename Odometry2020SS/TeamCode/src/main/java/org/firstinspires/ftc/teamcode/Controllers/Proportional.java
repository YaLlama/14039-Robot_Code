package org.firstinspires.ftc.teamcode.Controllers;
import java.lang.Math;

/*
This is a simple proportional control loop. 
*/

public class Proportional extends Controller {

    private double pGain;
    private double lim; // Upper limit
    private double error = 1000;
    private double offset;

    public Proportional(double p_Gain, double limit, double offset) {
        
        this.pGain = p_Gain;
        this.lim = limit;
        this.offset = offset;

    }

    public void setpGain(double pGain) {
        this.pGain = pGain;
    }

    public double getCorrection(double target, double current) {

        error = target - current;
        double correction = error * pGain;
        correction = correction + offset;
        
        if(correction > lim) {
            correction = lim;
        }
        if(correction < -lim) {
            correction = -lim;
        }
        
        return correction;
    }

    @Override
    public double getError() {
        return error;
    }
}
