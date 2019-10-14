package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Controllers.PID;

public class Drive extends Subsystem {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private OdometerRadians Adhameter;

    public boolean isRunning;

    public Drive(DcMotor Lf, DcMotor Rf, DcMotor Lb, DcMotor Rb, OdometerRadians Odometree) {

        this.frontLeft = Lf;
        this.frontRight = Rf;
        this.backLeft = Lb;
        this.backRight = Rb;
        this.Adhameter = Odometree;

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

        isRunning = true;

    }

    public void testMotors() {

        if(isRunning){
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

    public void pointInDirection(double direction) {
        ConstantP turn = new ConstantP(0.6, 30, 0.5);
        double correction = 10;

        while (Math.abs(correction) > 0.1) {
            if(isRunning) {
                correction = turn.getCorrection(direction, Adhameter.getHeadingDeg());

                frontLeft.setPower(-correction);
                backLeft.setPower(-correction);

                frontRight.setPower(correction);
                backRight.setPower(correction);

                Adhameter.updateOdometry();

            }else{
                break;
            }
        }
    }

    public void strafeToPoint(double x, double y) {

        PID hold = new PID(0.1, 0.02, 0.2, 15, 0.5);

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
