package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Drive;
import org.firstinspires.ftc.teamcode.Odometry.OdometerRadians;
import org.firstinspires.ftc.teamcode.Subsystem;

@Autonomous(name="Odometer Test", group="Linear Opmode")

public class OdometerTest extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;

    private Odometer2 Adham;
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
        RightFront = hardwareMap.dcMotor.get("rightEncoder");
        LeftFront = hardwareMap.dcMotor.get("leftEncoder");
        LeftBack = hardwareMap.dcMotor.get("backEncoder");
        RightBack = hardwareMap.dcMotor.get("rightBack");

        Adham = new Odometer2(RightFront, LeftFront, LeftBack, -1, -1, 1, this);
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
        int count = 0;

        while(opModeIsActive()) {
            telemetry.addData("heading", Adham.getHeadingAbsoluteDeg());
            telemetry.addData("X", Adham.getPosition()[0]);
            telemetry.addData("Y", Adham.getPosition()[1]);
            telemetry.update();

            Adham.updateOdometry();

            if(count%5 == 0) {
                Adham.integrate();
            }

            count++;
            
        }

        /*
        double initialX = Adham.getPosition()[0];
        double initialY = Adham.getPosition()[1];

        delay(2000);

        double changeX = Adham.getPosition()[0] - initialX;
        double changeY = Adham.getPosition()[1] - initialY;

        double distance = Math.sqrt(changeX * changeX + changeY * changeY);

        telemetry.addData("Drift", distance);
        telemetry.update();
         */
        //Make sure nothing is still using the thread
    }

    private void delay(int millis) {
        for(int x=0;x<millis; x++) {
            if (opModeIsActive()) {
                Adham.updateOdometry();
                try{Thread.sleep(1);}catch(InterruptedException e){e.printStackTrace();}
            }else {
                break;
            }
        }
    }

}
