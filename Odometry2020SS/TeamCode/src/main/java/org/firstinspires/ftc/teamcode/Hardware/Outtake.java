package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystem;

public class Outtake extends Subsystem {
    public boolean isRunning;

    private Extrusion VerticalExtrusion;
    private Servo GripperC;
    private Servo GripperP;
    private Servo Flipper;

    // Servo values for flipper
    private double flipped;
    private double inside;
    // Servo values for clamp
    private double clampedC;
    private double droppedC;
    private double clampedP;
    private double droppedP;

    public Outtake(Extrusion VerticalExtrusion, Servo GripperC, Servo GripperP, Servo Flipper) {
        this.VerticalExtrusion = VerticalExtrusion;
        this.Flipper = Flipper;
        this.GripperC = GripperC;
        this.GripperP = GripperP;

    }

    public void initialize(double clampedC, double droppedC, double clampedP, double droppedP, double flipped, double inside){
        this.clampedC = clampedC;
        this.droppedC = droppedC;
        this.clampedP = clampedP;
        this.droppedP = droppedP;
        this.flipped = flipped;
        this.inside = inside;

        GripperC.setPosition(droppedC);
        GripperP.setPosition(droppedP);
        Flipper.setPosition(inside);

    }

    // Autonomous Methods ==========================================================================

    // TeleOp Methods ==============================================================================

    public void dropManual(boolean trigger){
        isRunning = true;
        if(trigger) {
            GripperC.setPosition(droppedC);
        }else {
            GripperC.setPosition(clampedC);
        }
        isRunning = false;

    }

    public void flipManual(boolean trigger){
        isRunning = true;
        if(trigger) {
            Flipper.setPosition(flipped);
        }else {
            GripperC.setPosition(inside);
        }
        isRunning = false;

    }
}
