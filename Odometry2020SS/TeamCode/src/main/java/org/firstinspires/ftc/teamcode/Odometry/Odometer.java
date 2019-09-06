package org.firstinspires.ftc.teamcode.Odometry;

/*
Odometry means using sensors to track the movement of a robot. In this case, we are using
encoders on Omni's on the bottom of the robot. In the real world, Odometry like this is prone to
inaccuracy over time so it is usually  coupled with an external positioning system such as cameras
or distance sensors. In our case that shouldn't be needed.
*/

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Subsystem;

public class Odometer extends Subsystem{

    //Declare all objects needed for Odometry
    //Optical encoders
    private DcMotor rightEnc;
    private DcMotor leftEnc;
    //Important variables
    private double robotRad;
    private double encdrRad;

    private double x;
    private double y;
    private double heading;
    private double[] position = {0, 0};

    private double rightLastVal;
    private double leftLastVal;
    private final double ticksPerCM = 500;

    public boolean isRunning(){
        return true; //reee
    }

    public void doAction(String action){
        //IDK if this feature will be used, might be a pain
    }

    public Odometer(DcMotor rightEncoder, DcMotor leftEncoder, double botRadius, double encRadius){

        this.rightEnc = rightEncoder;
        this.leftEnc = leftEncoder;
        this.encdrRad = encRadius;
        this.robotRad = botRadius;

    }

    public void initializeOdometry(){

        x = 0;
        y = 0;
        rightEnc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEnc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void updateOdometry(){

    }

    public double[] getposition() {

        position[0] = x;
        position[1] = y;

        return position;

    }

}

