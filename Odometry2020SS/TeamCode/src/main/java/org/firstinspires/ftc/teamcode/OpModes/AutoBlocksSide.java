package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;

@Autonomous(name="Block Side Auto", group="Linear Opmode")

public class AutoBlocksSide extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private OdometerRadians Adham;
    private Drive Driver;

    private void initialize(){
        telemetry.addData("Status: ", "Initializing");
        telemetry.update();

        // Initialize all objects declared above
        RightFront = hardwareMap.dcMotor.get("rightEncoder");
        LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        LeftBack = hardwareMap.dcMotor.get("backEncoder");
        RightBack = hardwareMap.dcMotor.get("rightBack");

        Adham = new OdometerRadians(RightFront, LeftFront, LeftBack, -1, -1, 1, this);
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
        Driver.strafeToPointOrient(-22,14,0,2,2);
        Driver.strafeToPointOrient(-10,10,0,2,2);
        Driver.strafeToPointOrient(4,-6,0,2,2);
        Driver.strafeToPointOrient(-9,15,0,2,2);
        Driver.strafeToPointOrient(-11,2,0,2,2);
        Driver.strafeToPointOrient(0,0,0,2,2);



        //Make sure nothing is still using the thread
    }

    private void delay(int millis) {
        int limmit = (int)(millis/4);
        for(int x=0;x<millis; x++) {
            if (opModeIsActive()) {
                Adham.updateOdometry();
                try{Thread.sleep(4);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }

}