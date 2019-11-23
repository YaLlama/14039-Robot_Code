package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Odometry.Odometer2;

import org.firstinspires.ftc.teamcode.Hardware.Extrusion;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Outtake;

@TeleOp(name="Tele-Op", group="Linear Opmode")

public class TeleOpFinal extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private DcMotor intakeLeft;
    private DcMotor intakeRight;

    private DcMotor liftMotor1;
    private DcMotor liftMotor2;

    private DigitalChannel lowerLiftLimit;

    private Servo foundation1;
    private Servo foundation2;

    private Drive DriveTrain;
    private Odometer2 Adham;

    private Servo gripperServoP;
    private Servo gripperServoC;

    private Servo flipperServoR;
    private Servo flipperServoL;

    private Servo intakeDropper;

    private Intake Intake;
    private Extrusion Lift;
    private Outtake Stacker;

    private void initialize(){
        // Initialize all objects declared above

        rightFront = hardwareMap.dcMotor.get("rightEncoder");
        leftFront = hardwareMap.dcMotor.get("leftEncoder");
        leftBack = hardwareMap.dcMotor.get("backEncoder");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        intakeLeft = hardwareMap.dcMotor.get("leftIntake");
        intakeRight = hardwareMap.dcMotor.get("rightIntake");

        gripperServoP = hardwareMap.servo.get("gripperServoPaddle");
        gripperServoC = hardwareMap.servo.get("gripperServoClamp");

        flipperServoL = hardwareMap.servo.get("flipperServoLeft");
        flipperServoR = hardwareMap.servo.get("flipperServoRight");

        liftMotor1 = hardwareMap.dcMotor.get("liftMotor1");
        liftMotor2 = hardwareMap.dcMotor.get("liftMotor2");

        lowerLiftLimit = hardwareMap.digitalChannel.get("lowerLiftLimit");

        intakeDropper = hardwareMap.servo.get("intakeDropper");

        foundation1 = hardwareMap.servo.get("foundationLeft");
        foundation2 = hardwareMap.servo.get("foundationRight");

        Adham = new Odometer2(rightFront, leftFront, leftBack, null, -1, -1, -1, this);
        Adham.initialize(0, 0, 0);

        DriveTrain = new Drive(leftFront, rightFront, leftBack, rightBack, Adham, this);
        DriveTrain.initialize();

        Intake = new Intake(intakeLeft, intakeRight);
        Intake.initialize(-1, 1);

        Lift = new Extrusion(liftMotor1, liftMotor2, 2300, 475, lowerLiftLimit, this);
        Lift.initialize(0.1, 0.6);

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
            DriveTrain.handleDrive(driver, false);

            // Intake ==============================================================================
            Intake.intakeManual(scorer);

            // Outtake =============================================================================
            Lift.extrudeManual(-scorer.left_stick_y, 0);

            Stacker.dropManual(scorer.left_bumper);
            //ppStacker.flipManual(scorer.right_bumper);

            telemetry.addData("Outtake Position", liftMotor1.getCurrentPosition());
            telemetry.addData("Limit Switch", lowerLiftLimit.getState());
            telemetry.addData("Heading", Adham.getHeadingDeg());
            telemetry.update();

        }
    }


}