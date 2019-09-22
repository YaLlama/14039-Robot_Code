package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Drive extends Subsystem {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private boolean isRunning;

    public Drive(DcMotor Lf, DcMotor Rf, DcMotor Lb, DcMotor Rb) {

        this.frontLeft = Lf;
        this.frontRight = Rf;
        this.backLeft = Lb;
        this.backRight = Rb;

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

    private void delay(long millis) {
        try{Thread.sleep(millis);}catch(InterruptedException e){e.printStackTrace();}
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void doAction(String action) {

    }
}
