package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystem;
import org.firstinspires.ftc.teamcode.Drive;

@Autonomous(name="Odometer Calibration", group="Linear Opmode")
@Disabled
public class OdometryTest extends LinearOpMode {
    
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
        
        delay(800);
        
        telemetry.addData("Instruction: ", "Find a flat field area next to a wall");
        telemetry.update();
        
        delay(800);
        
        telemetry.addData("Instruction: ", "Use the wall to ensure that your robot turns excactly 360 deg");
        telemetry.update();
        
        delay(800);
        
        double turnCalibration;
        double turnAverage = 0;
        
        // Turn 1
        double initialHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        double endHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn complete, prepare for next turn");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        delay(800);
        
        // Turn 2
        initialHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        endHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn complete, prepare for next turn");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        // Turn 3
        initialHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn your robot 360 degrees counter-clockwise");
        telemetry.update();
        
        delay(2000);
        
        endHeading = Adham.getHeadingDeg();
        
        telemetry.addData("Instruction: ", "Turn complete, almost done");
        telemetry.update();
        
        turnCalibration = endHeading - initialHeading;
        turnAverage = turnCalibration + turnAverage;
        
        turnAverage = turnAverage/3;
        
        telemetry.addData("Update: ", "Test complete");
        telemetry.addData("Your percieved turn value is ", turnAverage);
        telemetry.update();
        
        //Now with your turnAverage, multiply it by your current robotRad and divide by 360;
        
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
