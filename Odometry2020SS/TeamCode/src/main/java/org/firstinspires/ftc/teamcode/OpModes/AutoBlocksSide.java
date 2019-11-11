package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;

import org.firstinspires.ftc.teamcode.SkystoneLocation;
import org.firstinspires.ftc.teamcode.CustomCV.SamplePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="Block Side Auto", group="Linear Opmode")

public class AutoBlocksSide extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private Odometer2 Adham;
    private Drive Driver;
    SamplePipeline pipeline;

    private void initialize(){

        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above
        RightFront = hardwareMap.dcMotor.get("rightEncoder");
        LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        LeftBack = hardwareMap.dcMotor.get("backEncoder");
        RightBack = hardwareMap.dcMotor.get("rightBack");

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, -1, -1, 1, this);
        Adham.initializeOdometry(0, 0);

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, this);
        Driver.initialize();

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();
        telemetry.addData("Status: ", "Running");
        telemetry.update();
        //Start Autonomous period

        Driver.strafeToPointOrient(20, 20, 180, 2, 2);
        delay(500);
        Driver.strafeToPointOrient(-20, -20, 0, 2, 2);
        delay(500);
        Driver.strafeToPointOrient(0, 0, 0, 2, 2);

        //Make sure nothing is still using the thread - End Autonomous period
    }

    private void delay(int millis) {
        int limit = (int)(millis/2);
        for(int x=0;x<limit; x++) {
            if (opModeIsActive()) {
                Driver.localize();
                try{Thread.sleep(2);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }

}