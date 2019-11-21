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
        Adham.initialize(23, 78, 0);


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
        initialize();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        telemetry.addData("Status: ", "Running");
        telemetry.update();
        //Start Autonomous period

        Driver.strafeToPointOrient(64, 95, 180, 2, 1);
        scanSkystone();
        delay(2000);
        if(skyPosition == 0) {
            Driver.strafeToPointOrient(94, 82, 180, 2, 1);
            blockHook.setPosition(0.1);
        }else if(skyPosition == 1) {
            Driver.strafeToPointOrient(94, 101, 180, 2, 1);
            blockHook.setPosition(0.1);
        }else if(skyPosition == 2) {
            Driver.strafeToPointOrient(64, 122, 180, 2, 1);
            blockHook.setPosition(0.1);
        }



        //Make sure nothing is still using the thread - End Autonomous period
    }

    private void scanSkystone(){
        skyPosition = pipeline.getSkystonePosition();
        if(skyPosition == 404) {
            scanSkystone();
        }
    }
    public void delay(int millis) {
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