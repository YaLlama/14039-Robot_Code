package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Odometer Test", group="Linear Opmode")
@Disabled
public class OdometryTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor EncoderRight;
    private DcMotor EncoderLeft;
    private DcMotor EncoderBack;

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
        EncoderRight = hardwareMap.dcMotor.get("rightEncoder");
        EncoderLeft = hardwareMap.dcMotor.get("leftEncoder");
        EncoderBack = hardwareMap.dcMotor.get("backEncoder");

        Adham = new OdometerRadians(EncoderRight, EncoderLeft, EncoderBack, 1, -1 ,1, this);
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
            telemetry.addData("Absolute Heading ", Adham.getHeadingAbsoluteDeg());
            telemetry.addData("X ", Adham.getPosition()[0]);
            telemetry.addData("Y ", Adham.getPosition()[1]);
            telemetry.update();

        }

        //Make sure nothing is still using the thread
    }
}