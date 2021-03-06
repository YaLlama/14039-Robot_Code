package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Subsystem;

@Disabled
@Autonomous(name="Bare Bones Auto", group="Linear Opmode")

public class BareAutoL extends LinearOpMode {

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

        //Make sure nothing is still using the thread
    }
}