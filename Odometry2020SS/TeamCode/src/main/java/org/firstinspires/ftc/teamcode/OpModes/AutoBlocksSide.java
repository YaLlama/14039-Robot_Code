package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

@Autonomous(name="Block Side Auto", group="Linear Opmode")

public class AutoBlocksSide extends LinearOpMode {

    private RobotHardware robot;

    // Important global variables
    private int skyPosition;

    public void initialize() {
        robot = new RobotHardware(this);

        robot.RightFront = hardwareMap.dcMotor.get("rightEncoder");
        robot.LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        robot.LeftBack = hardwareMap.dcMotor.get("backEncoder");
        robot.RightBack = hardwareMap.dcMotor.get("rightBack");

        robot.intakeLeft = hardwareMap.dcMotor.get("leftIntake");
        robot.intakeRight = hardwareMap.dcMotor.get("rightIntake");

        robot.blockHook = hardwareMap.servo.get("blockHook");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        robot.initialize(23, 78, 180, cameraMonitorViewId);

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

        robot.Driver.strafeToPointOrient(64, 95, 180, 2, 1);
        scanSkystone();

        //Make sure nothing is still using the thread - End Autonomous period
    }

    private void scanSkystone(){
        skyPosition = robot.pipeline.getSkystonePosition();
        if(skyPosition == 404) {
            scanSkystone();
        }
    }
}