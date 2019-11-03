package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystem;
import org.firstinspires.ftc.teamcode.Hardware.Drive;

@Autonomous(name="Odometer Calibration", group="Linear Opmode")
public class OdometerCalibrate extends LinearOpMode {
    
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
        
        telemetry.addData("Instruction: ", "This is a calibration program for your Odometer");
        telemetry.update();
        
        delay(200);
        
        telemetry.addData("Instruction: ", "Find a flat field area next to a wall");
        telemetry.update();
        
        delay(200);
        
        telemetry.addData("Instruction: ", "Use the wall to ensure that your robot turns excactly 360 deg");
        telemetry.update();
        
        delay(200);
        
        double turnCalibration;
        double turnAverage = 0;
        
        double backCalibration;
        double backAverage = 0;
        
        // Turn 1
        double initialHeading = Adham.getHeadingDeg();
        double initialBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        double endHeading = Adham.getHeadingDeg();
        double endBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn complete, prepare for next turn");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        backCalibration = endBack - initialBack;
        backAverage = backCalibration + backAverage;
        
        delay(400);
        
        // Turn 2
        initialHeading = Adham.getHeadingDeg();
        initialBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        endHeading = Adham.getHeadingDeg();
        endBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn complete, prepare for next turn");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        backCalibration = endBack - initialBack;
        backAverage = backCalibration + backAverage;
        
        delay(400);
        
        // Turn 3
        initialHeading = Adham.getHeadingDeg();
        initialBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        endHeading = Adham.getHeadingDeg();
        endBack = Adham.getBackReading();
        
        telemetry.addData("Instruction: ", "Turn complete, almost done");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        backCalibration = endBack - initialBack;
        backAverage = backCalibration + backAverage;
        
        delay(400);
        
        turnAverage = turnAverage/3;
        backAverage = backAverage/3;

        double robotRad = turnAverage * Adham.getRobotRad() / 360;
        double backRad = backAverage / 2 / Math.PI;
        
        telemetry.addData("Update: ", "Test complete");
        telemetry.addData("Your robot radius is ", turnAverage);
        telemetry.addData("Your back radius is ", backAverage);
        telemetry.update();

        delay(10000);
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
