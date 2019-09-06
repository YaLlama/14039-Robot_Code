package org.firstinspires.ftc.teamcode.Controllers;

//Abstract control-loop class

public abstract class Controller {

    abstract double getCorrection(double target, double currentError);

}
