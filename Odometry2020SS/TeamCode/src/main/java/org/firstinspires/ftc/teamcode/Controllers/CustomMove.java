package org.firstinspires.ftc.teamcode.Controllers;

public class CustomMove extends Controller{

    private double scale;
    private double error;
    private double correction;

    public CustomMove(double scale) {
        this.scale = scale;
    }

    public double getError() {
        return error;
    }

    @Override
    public double getCorrection(double target, double current) {
        error = target - current;
        correction = 0.2 * Math.pow(0.4*error, 0.3);
        if(correction > 0.4) {
            correction = 0.4;
        }
        if(error < 0) {
            correction = -correction;
        }

        return correction;
    }
}
