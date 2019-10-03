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
        delay(600);
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

        double heading;
        double xDiff;
        double yDiff;
        double direction;
        double directionRel;
        double distance = 1000000;
        double xComp;
        double yComp;
        double xCorrect;
        double yCorrect;

        while(distance > 5) {
            if(isRunning) {
                heading = Adhameter.getHeading();

                xDiff = x - Adhameter.getposition()[0];
                yDiff = y - Adhameter.getposition()[1];

                if(xDiff == 0) {
                    direction = 0;
                }else {
                    direction = 90 - Math.toDegrees(Math.atan(yDiff/xDiff));
                }

                directionRel = heading + 90 - direction;
                distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                xComp = cos(directionRel) * distance;
                yComp = sin(directionRel) * distance;

                xCorrect = hold.getCorrection(xComp, 0);
                yCorrect = hold.getCorrection(yComp, 0);

                frontLeft.setPower(yCorrect/2 + xCorrect);
                backLeft.setPower(yCorrect/2 - xCorrect);
                backRight.setPower(yCorrect/2 + xCorrect);
                frontRight.setPower(yCorrect/2 - xCorrect);

            }
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
