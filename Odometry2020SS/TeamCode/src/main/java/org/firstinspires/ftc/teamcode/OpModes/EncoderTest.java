package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Encoder Test", group="Linear Opmode")

public class EncoderTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor Encoder;

    private final double omniRadius = 1.84; //Radius of Omni wheels
    private final double gearing = 4/3; //How many times does the Omni spin for each spin of the encoder

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }

    private void initialize(){
        // Initialize all objects declared above
        Encoder = hardwareMap.dcMotor.get("Encoder");

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();

        telemetry.addData("Encoder Value ", Encoder.getCurrentPosition());

        while(opModeIsActive()) {
            telemetry.addData("Encoder Value ", Encoder.getCurrentPosition());
            telemetry.update();
        }

        //Make sure nothing is still using the thread
    }
}