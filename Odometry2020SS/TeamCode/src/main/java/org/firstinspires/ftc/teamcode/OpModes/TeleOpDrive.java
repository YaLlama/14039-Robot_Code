package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Drive;

@TeleOp(name="Teleop Chassis Test", group="Linear Opmode")

public class TeleOpDrive extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private Drive drivetrain;

    private void initialize(){
        // Initialize all objects declared above

        rightFront = hardwareMap.dcMotor.get("rightEncoder");
        leftFront = hardwareMap.dcMotor.get("leftEncoder");
        leftBack = hardwareMap.dcMotor.get("backEncoder");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        drivetrain = new Drive(leftFront, rightFront, leftBack, rightBack, null, this);
        drivetrain.initialize();

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

        double powerScale;
        double x1, x2, y1, y2;
        double lf, lb, rf, rb;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Driving =============================================================================
            if(gamepad1.left_bumper) {
                powerScale = 0.6;
            }else if(gamepad1.right_bumper) {
                powerScale = 0.2;
            }else {
                powerScale = 1;
            }

            y1 = -gamepad1.right_stick_y;
            x1 = -gamepad1.right_stick_x;
            x2 = -gamepad1.left_stick_x;
            y2 = -gamepad1.left_stick_y;

            rf = (y1 + x1) * powerScale;
            rb = (y1 - x1) * powerScale;
            lf = (y2 - x2) * powerScale;
            lb = (y2 + x2) * powerScale;

            leftFront.setPower(lf);
            leftBack.setPower(lb);
            rightFront.setPower(rf);
            rightBack.setPower(rb);

        }
    }
}