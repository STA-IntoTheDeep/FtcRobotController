package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drivetrain_new {

    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;
    double startup_x;
    double startup_y;

    public void init(HardwareMap map) {
        leftFront = map.get(DcMotorEx.class, "left_front");
        rightFront = map.get(DcMotorEx.class, "right_front");
        leftBack = map.get(DcMotorEx.class, "left_back");
        rightBack = map.get(DcMotorEx.class, "right_back");
        startup_x = rightFront.getCurrentPosition();
        startup_y = rightBack.getCurrentPosition();
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }
    //MotorX.setDirection(DcMotorSimple.Direction.REVERSE);

    //MotorX.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    public double pos_x() {
        return -rightFront.getCurrentPosition() + startup_x;
    }

    public double pos_y() {
        return rightBack.getCurrentPosition() - startup_y;
    }
    double maxPower;
    public void drive(double forward, double right, double rotate, double speed) {
        double leftFrontPower = -forward - right + rotate;
        double rightFrontPower = -forward + right - rotate;
        double rightRearPower = -forward - right - rotate;
        double leftRearPower = -forward + right + rotate;

        maxPower = speed;
        maxPower = Math.max(maxPower, Math.abs(leftFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightRearPower));
        maxPower = Math.max(maxPower, Math.abs(leftRearPower));

        leftFront.setPower(leftFrontPower / maxPower);
        rightFront.setPower(-rightFrontPower / maxPower);
        rightBack.setPower(-rightRearPower / maxPower);            //-- omdat gears
        leftBack.setPower(leftRearPower / maxPower);
    }

    public double getmaxpower(){
        return maxPower;
    }
}

