package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;

import org.firstinspires.ftc.teamcode.SkystoneLocation;
import org.firstinspires.ftc.teamcode.CustomCV.SamplePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="Block Side Auto", group="Linear Opmode")

public class AutoBlocksSide extends LinearOpMode {

    // Declare OpMode members ======================================================================
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private DcMotor intakeLeft;
    private DcMotor intakeRight;

    private Servo blockHook;

    private Odometer2 Adham;
    private Drive Driver;
    private Intake Intaker;

    private SamplePipeline pipeline;
    private OpenCvCamera phoneCam;

    // Important Variables =========================================================================
    private int skyPosition;

    private void initialize(){

        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above
        RightFront = hardwareMap.dcMotor.get("rightEncoder");
        LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        LeftBack = hardwareMap.dcMotor.get("backEncoder");
        RightBack = hardwareMap.dcMotor.get("rightBack");

        intakeLeft = hardwareMap.dcMotor.get("leftIntake");
        intakeRight = hardwareMap.dcMotor.get("rightIntake");

        blockHook = hardwareMap.servo.get("blockHook");
        blockHook.setPosition(1);

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, -1, -1, 1, this);
        Adham.initializeOdometry(0, 0);

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, this);
        Driver.initialize();

        Intaker = new Intake(intakeLeft, intakeRight);
        Intaker.initialize(-1, 1);

        // Vision
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();

        pipeline = new SamplePipeline();
        phoneCam.setPipeline(pipeline);
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

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
        Driver.strafeToPointOrient(-37, -25, 0, 2, 1);
        delay(50);
        scanSkystone();
        delay(200);

        if(skyPosition == 0) {
            Driver.strafeToPointOrient(-73, -52, 0, 2.2, 1.5);
            blockHook.setPosition(0.1);
            delay(500);
        }else if(skyPosition == 1) {
            Driver.strafeToPointOrient(-73, -33, 0, 2.2, 1.5);
            blockHook.setPosition(0.1);
            delay(500);
        }else if(skyPosition == 2) {
            Driver.strafeToPointOrient(-76, -14, 0, 2.2, 1.5);
            blockHook.setPosition(0.1);
            delay(500);
        }

        delay(50);
        Driver.strafeToPointOrient(-10, -40, 0, 2, 1.5);
        Driver.strafeToPointOrient(-13, -138, 0, 2, 1.5);
        blockHook.setPosition(1);
        delay(50);
        Driver.strafeToPointOrient(-49, 29, 4, 2, 1);
        delay(500);
        scanSkystone();
        if(skyPosition == 0) {
            Driver.strafeToPointOrient(-73, 14, 7, 2, 1);
            blockHook.setPosition(0.1);
            delay(500);
        }else if(skyPosition == 1) {
            Driver.strafeToPointOrient(-76, 35, 4, 2, 1);
            blockHook.setPosition(0.1);
            delay(500);
        }else if(skyPosition == 2) {
            Driver.strafeToPointOrient(-77, 52, 5, 2, 1);
            blockHook.setPosition(0.1);
            delay(500);
        }
        Driver.strafeToPointOrient(-10, -20, 0, 2, 1.5);
        Driver.strafeToPointOrient(-13, -138, 0, 2, 1.5);
        blockHook.setPosition(1);
        //Make sure nothing is still using the thread - End Autonomous period
    }

    private void scanSkystone(){
        skyPosition = pipeline.getSkystonePosition();

        if(skyPosition == 404) {
            scanSkystone();
        }
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