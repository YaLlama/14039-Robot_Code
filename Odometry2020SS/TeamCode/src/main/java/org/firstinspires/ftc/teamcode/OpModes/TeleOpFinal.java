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

    private Servo gripperServo;
    private Servo flipperServo;

    private Servo blockHook;

    private DcMotor liftMotor;
    private DigitalChannel lowerLiftLimit;

    private Drive DriveTrain;
    private Odometer2 Adham;

    private Servo clamper;
    private Servo blue;
    private Intake Intake;
    private Extrusion Lift;
    private Outtake Stacker;

    private void initialize(){
        // Initialize all objects declared above

        rightFront = hardwareMap.dcMotor.get("rightEncoder");
        leftFront = hardwareMap.dcMotor.get("leftEncoder");
        leftBack = hardwareMap.dcMotor.get("backEncoder");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        clamper = hardwareMap.servo.get("clamper");
        blue = hardwareMap.servo.get("bluepart");

        intakeLeft = hardwareMap.dcMotor.get("leftIntake");
        intakeRight = hardwareMap.dcMotor.get("rightIntake");

        gripperServo = hardwareMap.servo.get("blockHook"); // gripperServo
        flipperServo = hardwareMap.servo.get("flipperSeprvo");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        lowerLiftLimit = hardwareMap.digitalChannel.get("lowerLiftLimit");

        Adham = new Odometer2(rightFront, leftFront, leftBack, -1, -1, 1, this);
        Adham.initialize(0, 0, 0);

        DriveTrain = new Drive(leftFront, rightFront, leftBack, rightBack, Adham, this);
        DriveTrain.initialize();

        Intake = new Intake(intakeLeft, intakeRight);
        Intake.initialize(-1, 1);

        Lift = new Extrusion(liftMotor, null, 2300, 475, lowerLiftLimit, this);
        Lift.initialize(0.1, 0.6);

        Stacker = new Outtake(Lift, gripperServo, flipperServo);
        Stacker.initialize(0.3, 0, -1, 0);


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
            if(scorer.right_stick_y > 0.2){


            }

            // Driving =============================================================================
            DriveTrain.handleDrive(driver, false);

            // Intake ==============================================================================
            Intake.intakeManual(scorer);

            // Outtake =============================================================================
            Lift.extrudeManual(-scorer.left_stick_y, 0);

            Stacker.dropManual(scorer.left_bumper);
            //ppStacker.flipManual(scorer.right_bumper);

            telemetry.addData("Outtake Position", liftMotor.getCurrentPosition());
            telemetry.addData("Limit Switch", lowerLiftLimit.getState());
            telemetry.addData("Heading", Adham.getHeadingDeg());
            telemetry.update();

        }
    }


}