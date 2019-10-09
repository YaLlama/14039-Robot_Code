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
    private final double backRad;
    private final double encdrRad;
    private final double ticksPerRotation = 1450; //How many ticks are in 1 revolution of the encoder
    private double encScale;
    private double gear; //How many times does the Omni spin for each spin of the encoder

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

    private double xOffestLR;
    private double backOmniAdjust;
    private double backOmniExtra;

    private double[] posChangeLR = new double[2];
    private double[] posChangeB = new double[2];
    private double[] totalPosChange = new double[2];
    private double[] rotatedMovement = new double[2];

    public boolean isRunning = true;

    public double rightEncDir = 1;
    public double leftEncDir = -1;
    public double backEncDir = -1;

    public void doAction(String action){
        //IDK if this feature will be used, might be a pain
    }

    //3 Encoder objects, The distance from the L and R Omni's to the center, The distance from the back Omni to the center, the radius of the Omni
    public Odometer(DcMotor rightEncoder, DcMotor leftEncoder, DcMotor backEncoder, double botRadius, double backDistance, double encRadius, double gearRatio){

        this.rightEnc = rightEncoder;
        this.leftEnc = leftEncoder;
        this.backEnc = backEncoder;
        this.encdrRad = encRadius;
        this.robotRad = botRadius;
        this.backRad = backDistance;
        this.robotCir = 2*Math.PI*botRadius;
        this.gear = gearRatio;

    }

    public void initializeOdometry(){

        x = 0;
        y = 0;
        heading = 0;

        encScale = encdrRad*2*Math.PI/ticksPerRotation*gear;

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

        rightEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void updateOdometry(){

        if(isRunning){

            right = rightEnc.getCurrentPosition() * encScale * rightEncDir;
            left = leftEnc.getCurrentPosition() * encScale * leftEncDir;
            back = backEnc.getCurrentPosition() * encScale * backEncDir;

            // Calculates direction
            heading = (left - right)/2/robotCir*360;

            rightChange = right - rightLastVal;
            leftChange = left - leftLastVal;
            backChange = back - backLastVal;
            headingChange = heading - headingLastVal;

            //Calculating the position-change-vector from Left+Right encoders
            if(rightChange == leftChange) { // Robot has gone straight/not moved
                posChangeLR[0] = 0;
                posChangeLR[1] = rightChange;
            }if(rightChange < leftChange) { //Robot has turned to the left
                xOffestLR = Math.abs(leftChange * robotRad * 2/(rightChange - leftChange));
            }else{ //Robot has turned to the right
                xOffestLR = Math.abs(rightChange * robotRad * 2/(leftChange - rightChange));
            }

            if(Math.abs(rightChange) < Math.abs(leftChange)){
                posChangeLR[0] = cos(headingChange) * (xOffestLR + robotRad) - robotRad - xOffestLR;
                posChangeLR[1] = sin(headingChange) * (xOffestLR + robotRad);

            }else if(Math.abs(rightChange) > Math.abs(leftChange)){
                posChangeLR[0] = xOffestLR + robotRad - (cos(headingChange) * (xOffestLR + robotRad));
                posChangeLR[1] = sin(headingChange) * (xOffestLR + robotRad);

            }

            //Calculating the position-change-vector from back encoder

            backOmniAdjust = backRad * 2 * Math.PI * headingChange/360;
            backOmniExtra = backChange - backOmniAdjust;

            posChangeB[0] = cos(-headingChange) * backOmniExtra;
            posChangeB[1] = sin(-headingChange) * backOmniExtra;

            //Add the two vectors together
            totalPosChange[0] = posChangeLR[0] + posChangeB[0];
            totalPosChange[1] = posChangeLR[1] + posChangeB[1];

            //Rotate the vector;
            rotatedMovement[0] = totalPosChange[0] * cos(-headingLastVal) - totalPosChange[1] * sin(-headingLastVal);
            rotatedMovement[1] = totalPosChange[0] * sin(-headingLastVal) + totalPosChange[1] * cos(-headingLastVal);

            x = x + rotatedMovement[0];
            y = y + rotatedMovement[1];

            rightLastVal = right;
            leftLastVal = left;
            backLastVal = back;
            headingLastVal = heading;
        }
    }

    public double getHeading() {
        return heading;
    }

    public double[] getposition() {

        position[0] = x;
        position[1] = y;

        return position;

    }

    private double cos(double theta) {
        return Math.cos(Math.toRadians(theta));
    }

    private double sin(double theta) {
        return Math.sin(Math.toRadians(theta));
    }

}

