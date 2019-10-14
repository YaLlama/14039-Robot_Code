package org.firstinspires.ftc.teamcode.Controllers;
import java.lang.Math;

/*
This is a control loop that is best suited for turning a Mechanum-drive robot.
It corrects by a constant value until the reading gets close enough to the target, when
it switches over to a P controller.
*/

public class ConstantP extends Controller {

    private double pGain;
    private double pRange;
    private double constnt;

    public ConstantP(double constant, double p_Range, double p_Gain) {

        this.constnt = constant;
        this.pGain = p_Gain;
        this.pRange = p_Range;

    }

    public double getCorrection(double target, double current) {

        double error = target - current;
        double correction;
        if(Math.abs(error) > pRange) {
            correction = constnt * (error / Math.abs(error)); //getting the right sign for correction
        }else {
            correction = error * pGain;
        }
        return correction;

    }
}
