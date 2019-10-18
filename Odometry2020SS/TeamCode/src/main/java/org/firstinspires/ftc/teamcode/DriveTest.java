package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;

@Autonomous(name="Drive Test", group="Linear Opmode")

public class DriveTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private OdometerRadians Adham;
    private Drive Driver;

    public void doAction(Subsystem s, String action){
        while(s.isRunning){
            s.doAction(action);
        }
    }

    private void initialize(){
        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above
        RightFront = hardwareMap.dcMotor.get("RightEncoder");
        LeftFront = hardwareMap.dcMotor.get("LeftEncoder");
        LeftBack = hardwareMap.dcMotor.get("BackEncoder");
        RightBack = hardwareMap.dcMotor.get("RightBack");

        Adham = new OdometerRadians(RightFront, LeftFront, LeftBack, -1, -1, -1, this);
        Adham.initializeOdometry();

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham, this);
        Driver.initialize();

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
        //Start Autonomous period

        Driver.pointInDirection(90);

        //Make sure nothing is still using the thread
    }

    private void delay(int millis) {
        if (opModeIsActive()) {
            for(int x=0;x<millis; x++) {
                Adham.updateOdometry();
                try{Thread.sleep(1);}catch(InterruptedException e){e.printStackTrace();}
            }
        }
    }

}