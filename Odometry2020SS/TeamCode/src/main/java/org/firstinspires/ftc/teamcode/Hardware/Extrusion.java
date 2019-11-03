package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Controllers.PID;
import org.firstinspires.ftc.teamcode.Subsystem;

/* An extrusion class to automate all extrusion tasks
The direction of the motor must be such that a positive power causes the encoder to increase
 */
public class Extrusion extends Subsystem {

    private DcMotor motor1;
    private DcMotor motor2;

    private int upperLimit;
    private int lowerLimit;
    private DigitalChannel limitSwitch;
    private DigitalChannel lowSwitch;

    private PID run;

    private double powerLowLimit;

    private LinearOpMode op;

    public Extrusion(DcMotor m1, DcMotor m2, int upLimit, int lowLimit, DigitalChannel limSwitch, DigitalChannel loSwitch, LinearOpMode oppy) {
        this.motor1 = m1;
        this.motor2 = m2;
        this.upperLimit = upLimit;
        this.lowerLimit = lowLimit;
        this.limitSwitch = limSwitch;
        this.lowSwitch = loSwitch;
        this.op = oppy;

    }

    public void setPidConstants(double P, double I, double D) {
        run = new PID(P, I, D, 15, 0.7);
    }

    public void setPowerLowLimit(double limit) {
        powerLowLimit = limit;
    }

    public void initialize() {
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        lowSwitch.setMode(DigitalChannel.Mode.INPUT);

        motor1.setPower(0);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    //Autonomous Methods ===========================================================================

    public void runToPosition(double target) {
        double correct;
        while(op.opModeIsActive()) {
            correct = run.getCorrection(target, motor1.getCurrentPosition());
            if (Math.abs(correct) > powerLowLimit) {
                motor1.setPower(-correct);
            }else {
                break;
            }
        }
    }

    private void reachUpperLimit() {
        while(op.opModeIsActive()) {
            if (limitSwitch.getState()) {

            }
        }
    }

    public void extend(String method) {
        if(method.equals("encoder")) {
            runToPosition(upperLimit - 10);
        }else if(method.equals("switch")) {

        }
    }

    public void retract(String method) {

    }


    public void doAction(String action) {

    }
}
