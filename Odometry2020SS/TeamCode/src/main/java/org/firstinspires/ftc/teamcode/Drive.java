package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Odometry.Odometer;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Controllers.PID;

public class Drive extends Subsystem {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private Odometer Adhameter;

    public boolean isRunning;

    public Drive(DcMotor Lf, DcMotor Rf, DcMotor Lb, DcMotor Rb, Odometer odometree) {

        this.frontLeft = Lf;
        this.frontRight = Rf;
        this.backLeft = Lb;
        this.backRight = Rb;
        this.Adhameter = odometree;

    }

    public void initialize() {

        isRunning = true;
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        isRunning = false;

    }

    public void testMotors() {

        isRunning = true;
        initialize();
        frontRight.setPower(0.4);
        delay(500);
        frontRight.setPower(0);
        frontLeft.setPower(0.4);
        delay(500);
        frontLeft.setPower(0);
        backRight.setPower(0.4);
        delay(500);
        backRight.setPower(0);
        backLeft.setPower(0.4);
        delay(500);
        backLeft.setPower(0);
        isRunning = false;

    }

    public void pointInDirection(double direction) {
        ConstantP turn = new ConstantP(0.6, 30, 0.5);
        double correction = turn.getCorrection(direction, Adhameter.getHeading());
        if(isRunning) {
            while (Math.abs(correction) < 0.1) {
                frontLeft.setPower(correction);
                backLeft.setPower(correction);

                frontRight.setPower(-correction);
                backRight.setPower(-correction);

                correction = turn.getCorrection(direction, Adhameter.getHeading());
                Adhameter.updateOdometry();
            }
        }
    }

    public void strafeToPoint(double x, double y) {
        PID hold = new PID(0.1, 0.02, 0.2, 15, 0.5);

        double xDiff = x - Adhameter.getposition()[0];
        double yDiff = y - Adhameter.getposition()[1];

        double heading = Adhameter.getHeading();

        double xAdjusted = xDiff * cos(-heading) - yDiff * sin(-heading);
        double yAdjusted = xDiff * sin(-heading) + yDiff * cos(-heading);

        if(isRunning) {
            //placehold
        }
    }

    private void delay(int millis) {
        for(int x=0;x<millis; x++) {
            Adhameter.updateOdometry();
            try{Thread.sleep(1);}catch(InterruptedException e){e.printStackTrace();}
        }
    }

    public void doAction(String action) {

    }

    private double cos(double theta) {
        return Math.cos(Math.toRadians(theta));
    }

    private double sin(double theta) {
        return Math.sin(Math.toRadians(theta));
    }

}
