package org.firstinspires.ftc.teamcode.CustomCV;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.CustomCV.SamplePipeline;
import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;
import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;
import org.firstinspires.ftc.teamcode.SkystoneLocation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@TeleOp(name="Vision Test", group = "Linear Opmode")
public class VisionTest extends LinearOpMode {

    private OpenCvCamera phoneCam;
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private String skystring;

    private Odometer2 Adham;

    private Drive Driver;


    private void initialize(){
        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above

        RightFront = hardwareMap.dcMotor.get("rightEncoder");
        LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        LeftBack = hardwareMap.dcMotor.get("backEncoder");
        RightBack = hardwareMap.dcMotor.get("rightBack");

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, -1, -1, 1, this);
        Adham.initialize(x, y, theta);

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, this);
        Driver.initialize();
        
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();

        SamplePipeline pipeline = new SamplePipeline();

        phoneCam.setPipeline(pipeline);

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

    }

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        telemetry.addData("Status: ", "Running");
        telemetry.update();

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        while (opModeIsActive())
        {
            telemetry.addData("FPS", String.format("%.2f", phoneCam.getFps()));
            telemetry.addData("location", pipeline.location);
            telemetry.addData("type", pipeline.location.getClass());
            telemetry.update();

            sleep(100);
        }
    }
}
