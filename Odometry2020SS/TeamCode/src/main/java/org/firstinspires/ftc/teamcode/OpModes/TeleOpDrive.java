package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;

@TeleOp(name="Teleop Chassis Test", group="Linear Opmode")

public class TeleOpDrive extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private DcMotor intakeLeft;
    private DcMotor intakeRight;

    private Servo blockHook;

    private Drive Drivetrain;
    private Odometer2 Adham;
    private Intake Intake;

    private BNO055IMU Imu;
    private BNO055IMU.Parameters Params;

    private void initialize(){
        // Initialize all objects declared above

        rightFront = hardwareMap.dcMotor.get("rightEncoder");
        leftFront = hardwareMap.dcMotor.get("leftEncoder");
        leftBack = hardwareMap.dcMotor.get("backEncoder");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        intakeLeft = hardwareMap.dcMotor.get("leftIntake");
        intakeRight = hardwareMap.dcMotor.get("rightIntake");

        Params = new BNO055IMU.Parameters();
        Params.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        Params.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        Params.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opMode
        Params.loggingEnabled      = true;
        Params.loggingTag          = "IMU";
        Params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        Imu = hardwareMap.get(BNO055IMU.class, "imu");

        blockHook = hardwareMap.servo.get("blockHook");

        //==========================================================================================
        Imu.initialize(Params);

        Adham = new Odometer2(rightFront, leftFront, leftBack, Imu, -1, -1, -1, this);
        Adham.initialize(0, 0, 0);

        Drivetrain = new Drive(leftFront, rightFront, leftBack, rightBack, Adham, this);
        Drivetrain.initialize();

        Intake = new Intake(intakeLeft, intakeRight);
        Intake.initialize(-1, 1);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        Gamepad driver = gamepad1;
        Gamepad scorer = gamepad2;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Driving =============================================================================
            Drivetrain.handleDrive(driver, false);

            // Intake ==============================================================================
            Intake.intakeManual(scorer);

            blockHook.setPosition(scorer.left_stick_y);

            Drivetrain.localize();
            telemetry.addData("ServoPosition", blockHook.getPosition());
            telemetry.addData("X", Adham.getPosition()[0]);
            telemetry.addData("Y", Adham.getPosition()[1]);
            telemetry.addData("Heading", Adham.getHeadingDeg());
            telemetry.update();

        }
    }


}