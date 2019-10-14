package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Odometer Test", group="Linear Opmode")

public class OdometryTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor EncoderRight;
    private DcMotor EncoderLeft;
    private DcMotor EncoderBack;

    private final double omniRadius = 1.85; //Radius of Omni wheels
    private final double gearing = 1.5; //How many times does the Omni spin for each spin of the encoder
    private final double robotRadius = 9.5;
    private final double distanceBack = 31;

    private OdometerRadians Adham;

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }

    private void initialize(){
        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above
        EncoderRight = hardwareMap.dcMotor.get("RightEncoder");
        EncoderLeft = hardwareMap.dcMotor.get("LeftEncoder");
        EncoderBack = hardwareMap.dcMotor.get("BackEncoder");

        Adham = new OdometerRadians(EncoderRight, EncoderLeft, EncoderBack, robotRadius, distanceBack, omniRadius, gearing);
        Adham.initializeOdometry();

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();
        telemetry.addData("Status: ", "Running");
        telemetry.update();

        while(opModeIsActive()) {

            Adham.updateOdometry();

            telemetry.addData("Heading ", Adham.getHeadingDeg());
            telemetry.addData("X ", Adham.getposition()[0]);
            telemetry.addData("Y ", Adham.getposition()[1]);
            telemetry.update();

        }

        //Make sure nothing is still using the thread
    }
}