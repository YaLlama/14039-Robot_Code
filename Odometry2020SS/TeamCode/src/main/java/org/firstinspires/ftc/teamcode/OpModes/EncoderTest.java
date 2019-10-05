package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Encoder Test", group="Linear Opmode")

public class EncoderTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor Encoder;
    private DcMotor Encoder1;
    private DcMotor Encoder2;

    private final double omniRadius = 1.84; //Radius of Omni wheels
    private final double gearing = 4/3; //How many times does the Omni spin for each spin of the encoder
    private final double ticksPerRotation = 360;

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }

    private void initialize(){
        // Initialize all objects declared above
        Encoder = hardwareMap.dcMotor.get("RightEncoder");
        Encoder1 = hardwareMap.dcMotor.get("LeftEncoder");
        Encoder2 = hardwareMap.dcMotor.get("BackEncoder");

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();

        double encScale = omniRadius*2*Math.PI/ticksPerRotation/gearing;

        Encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Encoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Encoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while(opModeIsActive()) {

            telemetry.addData("Right Encoder Value ", Encoder.getCurrentPosition() * encScale);
            telemetry.addData("Left Encoder Value ", Encoder1.getCurrentPosition() * encScale);
            telemetry.addData("Back Encoder Value ", Encoder2.getCurrentPosition() * encScale);
            telemetry.update();

        }

        //Make sure nothing is still using the thread
    }
}