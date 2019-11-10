package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Controllers.Proportional;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;
import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Controllers.PID;
import org.firstinspires.ftc.teamcode.Subsystem;

public class Drive extends Subsystem {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private Odometer2 Adhameter;

    private LinearOpMode opmode;

    private int count;

    public boolean isRunning;
    public boolean stopped;

    public Drive(DcMotor Lf, DcMotor Rf, DcMotor Lb, DcMotor Rb, Odometer2 Odometree, LinearOpMode oppy) {

        this.frontLeft = Lf;
        this.frontRight = Rf;
        this.backLeft = Lb;
        this.backRight = Rb;
        this.Adhameter = Odometree;
        this.opmode = oppy;

    }

    public void initialize() {

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        count = 0;

        isRunning = true;

    }

    public void testMotors() {

        if(opmode.opModeIsActive()){
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
        }
    }

    //Autonomous Methods ===========================================================================

    public void pointInDirection(double direction) { // Verified
        ConstantP turn = new ConstantP(0.5, 20, 0.025);
        double correction = 10;

        while (Math.abs(correction) > 0.1) {
            if(opmode.opModeIsActive()) {
                correction = turn.getCorrection(direction, Adhameter.getHeadingDeg());

                frontLeft.setPower(-correction);
                backLeft.setPower(-correction);

                frontRight.setPower(correction);
                backRight.setPower(correction);

                localize();

            }else{
                break;
            }
        }
    }

    public void pointInDirectionRough(double direction, double threshold) { // Verified

        ConstantP turn = new ConstantP(0.6, 30, 0.5);
        double correction = 10;
        double error = Adhameter.getHeadingDeg() - direction;

        while (Math.abs(error) > threshold) {
            if(opmode.opModeIsActive()) {
                correction = turn.getCorrection(direction, Adhameter.getHeadingDeg());

                frontLeft.setPower(-correction);
                backLeft.setPower(-correction);

                frontRight.setPower(correction);
                backRight.setPower(correction);

                error = Adhameter.getHeadingDeg() - direction;
                localize();

            }else{
                break;
            }
        }
    }
            
    public void strafeToPointOrient(double x, double y, double heading, double posThreshold, double headThreshold) { // Verified

        count = 0;

        PID holdX = new PID(0.05, 0.002, 0.01, 7, 0.3);
        PID holdY = new PID(0.05, 0.002, 0.01, 7, 0.3);
        Proportional orient = new Proportional(0.02, 0.4);
        
        double Xdiff = x - Adhameter.getPosition()[0];
        double Ydiff = y - Adhameter.getPosition()[1];
        double distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);
        
        while(distance > posThreshold || Math.abs(orient.getError()) > headThreshold) {
            if (opmode.opModeIsActive()) {
                
                Xdiff = x - Adhameter.getPosition()[0];
                Ydiff = y - Adhameter.getPosition()[1];
                distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);
                
                double h = Adhameter.getHeadingDeg();
                
                double XD = cos(-h) * Xdiff - sin(-h) * Ydiff;
                double YD = sin(-h) * Xdiff + cos(-h) * Ydiff;
                
                double hCorrect = orient.getCorrection(heading, h);
                double xCorrect = holdX.getCorrection(0, XD);
                double yCorrect = holdY.getCorrection(0, YD);
                
                frontLeft.setPower(-xCorrect - yCorrect - hCorrect);
                backLeft.setPower(xCorrect - yCorrect - hCorrect);

                frontRight.setPower(xCorrect - yCorrect + hCorrect);
                backRight.setPower(-xCorrect - yCorrect + hCorrect);

                localize();

            }else {
                break;
            }
        }
    }

    public void goToPointStraight(double x, double y, double threshold) {

        double Xdiff = x - Adhameter.getPosition()[0];
        double Ydiff = y - Adhameter.getPosition()[1];
        double direction;
        double distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);

        while (distance > threshold) {
            if (opmode.opModeIsActive()) {

                Xdiff = y - Adhameter.getPosition()[0];
                Ydiff = x - Adhameter.getPosition()[1];
                direction = Math.toDegrees(Math.atan(Ydiff/Xdiff));
                distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);

                pointInDirectionRough((direction-90), 10);

                frontLeft.setPower(0.5);
                backLeft.setPower(0.5);
                frontRight.setPower(0.5);
                backRight.setPower(0.5);

                delay(500);
                localize();

            }else {
                break;
            }
        }
    }

    public void goToPointCurve(double x, double y, double power, double threshold) {

        double Xdiff = x - Adhameter.getPosition()[0];
        double Ydiff = y - Adhameter.getPosition()[1];
        double direction;
        double distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);

        Proportional orient = new Proportional(0.02, 0.9 - power);

        while (distance > threshold) {
            if (opmode.opModeIsActive()) {

                Xdiff = y - Adhameter.getPosition()[0];
                Ydiff = x - Adhameter.getPosition()[1];
                direction = Math.toDegrees(Math.atan(Ydiff/Xdiff));
                distance = Math.sqrt(Xdiff * Xdiff + Ydiff * Ydiff);

                double correct = orient.getCorrection(direction, Adhameter.getHeadingDeg());

                frontLeft.setPower(power - correct);
                backLeft.setPower(power - correct);

                frontRight.setPower(power + correct);
                backRight.setPower(power + correct);

                localize();

            }else {
                break;
            }
        }
    }

    public void localize() {
        Adhameter.updateOdometry();
        if(count%6 == 0) {
            Adhameter.integrate();
        }
        count++;
    }

    private void delay(int millis) {
        int limit = (int)(millis/2);
        for(int x=0;x<limit; x++) {
            if (opmode.opModeIsActive()) {
                localize();
                try{Thread.sleep(2);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }

    //Continuous Methods ===========================================================================

    private double cos(double theta) {
        return Math.cos(Math.toRadians(theta));
    }

    private double sin(double theta) {
        return Math.sin(Math.toRadians(theta));
    }

}
