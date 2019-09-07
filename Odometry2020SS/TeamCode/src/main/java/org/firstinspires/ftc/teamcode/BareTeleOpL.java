package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Bare Teleop", group="Linear Opmode")

public class BareTeleOpL extends LinearOpMode {

    // Declare OpMode members.


    private void initialize(){
        // Initialize all objects declared above

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }


    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            telemetry.update();
        }
    }
}