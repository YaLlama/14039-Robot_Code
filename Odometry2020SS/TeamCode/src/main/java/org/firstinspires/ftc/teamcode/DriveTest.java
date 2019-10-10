package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Controllers.ConstantP;
import org.firstinspires.ftc.teamcode.Odometry.Odometer;

@Autonomous(name="Drive Test", group="Linear Opmode")

public class DriveTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private final double omniRadius = 1.85; //Radius of Omni wheels
    private final double gearing = 1.5; //How many times does the Omni spin for each spin of the encoder
    private final double robotRadius = 9.5;
    private final double distanceBack = 31;

    private Odometer Adham;
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

        Adham = new Odometer(RightFront, LeftFront, LeftBack, robotRadius, distanceBack, omniRadius, gearing);
        Adham.initializeOdometry();

        Driver = new Drive(LeftFront, RightFront, LeftBack, RightBack, Adham);
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

        pointInDirection(90);

        //Make sure nothing is still using the thread
    }

    private void pointInDirection(double direction) {
        ConstantP turn = new ConstantP(0.6, 30, 0.5);
        double correction = 10;
        if(opModeIsActive()) {
            while (Math.abs(correction) > 0.4) {
                correction = turn.getCorrection(direction, Adham.getHeading());

                LeftFront.setPower(correction);
                LeftBack.setPower(correction);

                RightFront.setPower(-correction);
                RightBack.setPower(-correction);

                telemetry.addData("correction ", correction);
                telemetry.update();

                Adham.updateOdometry();
            }
        }
    }

    private void delay(int millis) {
        for(int x=0;x<millis; x++) {
            Adham.updateOdometry();
            try{Thread.sleep(1);}catch(InterruptedException e){e.printStackTrace();}
        }
    }

}