package org.firstinspires.ftc.teamcode.Odometry;

/*
Odometry means using sensors to track the movement of a robot. In this case, we are using
encoders on Omni's on the bottom of the robot. In the real world, Odometry like this is prone to
inaccuracy over time so it is usually  coupled with an external positioning system such as cameras
or distance sensors. In our case that shouldn't be needed.
*/

import android.preference.PreferenceActivity;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Subsystem;
import java.lang.Math;

public class Odometer extends Subsystem{

    //Declare all objects needed for Odometry
    //Optical encoders
    private DcMotor rightEnc;
    private DcMotor leftEnc;
    private DcMotor backEnc;
    //Important variables
    private final double robotCir;
    private final double robotRad;
    private final double encdrRad;
    private final double ticksPerRotation = 500;
    private double encScale;

    private double x;
    private double y;
    private double heading;
    private double[] position = {0, 0};

    private double right;
    private double left;
    private double back;

    private double rightLastVal;
    private double leftLastVal;
    private double backLastVal;
    private double headingLastVal;

    private double rightChange;
    private double leftChange;
    private double backChange;
    private double headingChange;
    private double headChangeRad;

    private double vectorAngleLR;

    private double xOffestLR;

    private double[] posChangeLR = new double[2];
    private double[] posChangeB = new double[2];

    private boolean isRunning;

    public boolean isRunning(){
        return isRunning;
    }

    public void doAction(String action){
        //IDK if this feature will be used, might be a pain
    }

    public Odometer(DcMotor rightEncoder, DcMotor leftEncoder, DcMotor backEncoder, double botRadius, double encRadius){

        this.rightEnc = rightEncoder;
        this.leftEnc = leftEncoder;
        this.backEnc = backEncoder;
        this.encdrRad = encRadius;
        this.robotRad = botRadius;
        this.robotCir = 2*Math.PI*botRadius;

    }

    public void initializeOdometry(){

        x = 0;
        y = 0;
        heading = 0;

        encScale = encdrRad*2*Math.PI/ticksPerRotation;

        right = 0;
        left = 0;
        back = 0;

        rightChange = 0;
        leftChange = 0;
        backChange = 0;
        headingChange = 0;

        rightLastVal = 0;
        leftLastVal = 0;
        backLastVal = 0;

        rightEnc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEnc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backEnc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void updateOdometry(){

        right = rightEnc.getCurrentPosition() * encScale;
        left = leftEnc.getCurrentPosition() * encScale;
        back = backEnc.getCurrentPosition() * encScale;

        // Calculates direction
        heading = (left - right)/2/robotCir*360;

        rightChange = right - rightLastVal;
        leftChange = left - leftLastVal;
        backChange = back - backLastVal;
        headingChange = heading - headingLastVal;
        headChangeRad = Math.toRadians(headingChange);

        //Calculating the position-change-vector from Left+Right encoders
        if(rightChange == leftChange) { // Robot has gone straight/not moved

            posChangeLR[0] = 0;
            posChangeLR[1] = rightChange;

        }else if(rightChange > leftChange) { // Robot has turned to the left

        }else { //Robot has turned to the right

        }

        //Calculating the position-change-vector from back encoder

        rightLastVal = right;
        leftLastVal = left;
        backLastVal = back;
        headingLastVal = heading;

    }

    public double getHeading() {
        return heading;
    }

    public double[] getposition() {

        position[0] = x;
        position[1] = y;

        return position;

    }

}

