package org.firstinspires.ftc.teamcode.Hardware;
/*
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CustomCV.SamplePipeline;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class RobotHardware {

    public DcMotor RightFront;
    public DcMotor RightBack;
    public DcMotor LeftFront;
    public DcMotor LeftBack;

    public DcMotor intakeLeft;
    public DcMotor intakeRight;

    public Servo blockHook;

    public Odometer2 Adham;
    public Drive Driver;
    public Intake Intaker;

    public SamplePipeline pipeline;
    public OpenCvCamera phoneCam;

    private LinearOpMode opMode;

    public RobotHardware(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void initialize(double x, double y, double theta, int cameraMonitorViewId) {
        blockHook.setPosition(1);

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, opMode);
        Driver.initialize();

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, -1, -1, 1, opMode);
        Adham.initialize(x, y, theta);

        Intaker = new Intake(intakeLeft, intakeRight);
        Intaker.initialize(-1, 1);

        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();

        pipeline = new SamplePipeline();
        phoneCam.setPipeline(pipeline);
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

    }

    public void delay(int millis) {
        int limit = (int)(millis/2);
        for(int x=0;x<limit; x++) {
            if (opMode.opModeIsActive()) {
                Driver.localize();
                try{Thread.sleep(2);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }

}
*/