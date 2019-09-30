package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Odometry.Odometer;
import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Encoder Test", group="Linear Opmode")

public class EncoderTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor rightEnc;
    private DcMotor leftEnc;
    private DcMotor backEnc;

    private final double robotRadius = 14; //Distance from center to left and right Omni's
    private final double robotBackRadius = 17; //Distance from center to back Omni
    private final double omniRadius = 1.84; //Radius of Omni wheels
    private final double gearing = 4/3; //How many times does the Omni spin for each spin of the encoder


    private Odometer Adham = new Odometer(rightEnc, leftEnc, backEnc, robotRadius, robotBackRadius, omniRadius, gearing);

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }

    private void initialize(){
        // Initialize all objects declared above
        rightEnc = hardwareMap.dcMotor.get("RightEncoder");
        leftEnc = hardwareMap.dcMotor.get("LeftEncoder");
        backEnc = hardwareMap.dcMotor.get("BackEncoder");

        Adham.initializeOdometry();

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();

        telemetry.addData("Heading ", Adham.getHeading());
        telemetry.addData("X ", Adham.getposition()[0]);
        telemetry.addData("Y ", Adham.getposition()[1]);

        while(opModeIsActive()) {
            telemetry.update();
        }

        waitForStart();

        //Make sure nothing is still using the thread
    }
}