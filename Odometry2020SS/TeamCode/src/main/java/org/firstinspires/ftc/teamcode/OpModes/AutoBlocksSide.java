package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
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

    private BNO055IMU Imu;
    private BNO055IMU.Parameters Params;

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

        Params = new BNO055IMU.Parameters();
        Params.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        Params.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        Params.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opMode
        Params.loggingEnabled      = true;
        Params.loggingTag          = "IMU";
        Params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        Imu = hardwareMap.get(BNO055IMU.class, "imu");

        //==========================================================================================
        Imu.initialize(Params);

        blockHook.setPosition(0);

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, Imu, -1, -1, -1, this);
        Adham.initialize(0, 0, 0);

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

        Driver.strafeToPointOrient(-40, -11, 0, 2, 1);
        delay(10);
        scanSkystone();
        phoneCam.closeCameraDevice();
        delay(10);

        if(skyPosition == 0) {
            Driver.strafeToPointOrient(-77, -47, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }else if(skyPosition == 1) {
            Driver.strafeToPointOrient(-77, -31, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }else if(skyPosition == 2) {
            Driver.strafeToPointOrient(-77, -7, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }

        Driver.strafeToPointOrient(-46, -73, 0, 2.5, 1.5);
        Driver.strafeToPointOrient(-59, -129, 0, 2, 1);
        blockHook.setPosition(0);

        Driver.strafeToPointOrient(-46, -73, 0, 2.5, 1.5);
        Driver.strafeToPointOrient(-35, 12, 0, 2, 1);

        if(skyPosition == 0) {
            Driver.strafeToPointOrient(-77, 9, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }else if(skyPosition == 1) {
            Driver.strafeToPointOrient(-77, 30, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }else if(skyPosition == 2) {
            Driver.strafeToPointOrient(-77, 51, 0, 2, 1);
            blockHook.setPosition(0.6);
            delay(300);
        }

        Driver.strafeToPointOrient(-38, -73, 0, 2, 1);
        Driver.strafeToPointOrient(-59, -129, 0, 2, 1);
        blockHook.setPosition(0);

        // Park
        Driver.strafeToPointOrient(-65, -97, 0, 2, 1);

        //Make sure nothing is still using the thread - End Autonomous period
    }

    private void scanSkystone(){
        skyPosition = pipeline.getSkystonePosition();
        if(skyPosition == 404) {
            scanSkystone();
        }
    }

    private void delay(int millis) {
        int limit = (int)(millis/4);
        for(int x=0;x<limit; x++) {
            if (opModeIsActive()) {
                Driver.localize();
                try{Thread.sleep(4);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }
}