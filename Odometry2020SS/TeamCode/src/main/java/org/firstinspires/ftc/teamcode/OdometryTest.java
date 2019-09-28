package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Odometry.Odometer;


@Autonomous(name="Bare Bones Auto", group="Linear Opmode")

public class OdometryTest extends LinearOpMode {

    // Declare OpMode members.
    DcMotor right;
    DcMotor left;
    DcMotor back;

    private final double robotRadius = 10; //Distance from center to left and right Omni's
    private final double robotBackRadius = 10; //Distance from center to back Omni
    private final double omniRadius = 3; //Radius of Omni wheels


    Odometer Adahm = new Odometer(right, left, back, robotRadius, robotBackRadius, omniRadius);

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }


    private void initialize(){
        // Initialize all objects declared above



        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }


    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();


        waitForStart();

        //Make sure nothing is still using the thread
    }
}