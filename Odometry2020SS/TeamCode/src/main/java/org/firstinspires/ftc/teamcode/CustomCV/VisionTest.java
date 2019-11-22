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

import java.nio.file.AccessDeniedException;

@TeleOp(name="Vision Test", group = "Linear Opmode")
public class VisionTest extends LinearOpMode {

    private OpenCvCamera phoneCam;
    private SamplePipeline pipeline;

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
        Adham.initialize(0, 0, 0);

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, this);
        Driver.initialize();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();

        pipeline = new SamplePipeline();

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

        while (opModeIsActive()) {
            telemetry.addData("x", Adham.getPosition()[0]);
            telemetry.addData("Y", Adham.getPosition()[1]);
            telemetry.addData("H", Adham.getHeadingAbsoluteDeg());
            telemetry.update();

            Driver.localize();

            sleep(100);
        }
    }
}
