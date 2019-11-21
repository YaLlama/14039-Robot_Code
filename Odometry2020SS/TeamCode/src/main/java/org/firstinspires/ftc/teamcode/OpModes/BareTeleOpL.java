package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Bare Teleop", group="Linear Opmode")


public class BareTeleOpL extends LinearOpMode {

    // Declare OpMode members.
    private Servo clamper;
    private Servo blue;
    double r=0;
    double p=0;
    private void initialize(){
        // Initialize all objects declared above
        clamper = hardwareMap.servo.get("clamper");
        blue = hardwareMap.servo.get("bluepart");
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

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if(gamepad2.right_stick_y > 0.2){
                clamper.setPosition(r);

                r= r+0.05;

            } else if(gamepad2.right_stick_y < 0.2){
                clamper.setPosition(r);
                r= r - 0.05;

            }

            if(gamepad2.left_stick_y > 0.2){
                blue.setPosition(r);

                p= p+0.05;

            } else if(gamepad2.left_stick_y < 0.2){
                blue.setPosition(r);
                p= p - 0.05;

            }

            telemetry.update();
        }
    }
}