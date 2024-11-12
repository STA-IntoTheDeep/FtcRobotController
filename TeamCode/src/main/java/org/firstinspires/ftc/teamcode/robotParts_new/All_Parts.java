package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class partcount {
    int servos = 1;
}


public class All_Parts {

    private Servo servo0;
    private DcMotorEx lf;
    private DcMotorEx rf;
    private DcMotorEx lb;
    private DcMotorEx rb;
    //private DcMotorEx arm;
    //private DcMotorEx arm2;
    partcount c = new partcount();
    Servo[] servo = {servo0};

    public void init(HardwareMap map) {
        /*
        for (int a = 0; a <= (c.servos-1); a++) {
            servo[a] = map.get(Servo.class, "servo" + a);
        }
         */
        lf = map.get(DcMotorEx.class, "left_front");
        rf = map.get(DcMotorEx.class, "right_front");
        lb = map.get(DcMotorEx.class, "left_back");
        rb = map.get(DcMotorEx.class, "right_back");
        //arm = map.get(DcMotorEx.class, "arm");
        //arm2 = map.get(DcMotorEx.class, "arm");
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void servo0(int which, double pos) {
        servo[which].setPosition(pos);
    }

    /*
    public void armRotate(double p1, double p2) {
        arm.setPower(p1);
        arm2.setPower(p2);
    }

    public int[] armPos() {
        return new int[]{arm.getCurrentPosition(), arm2.getCurrentPosition()};
    }
     */

    public int posY(){
        return rb.getCurrentPosition();
    }
    public int posX(){
        return rf.getCurrentPosition();
    }

    public void drive0(double forward, double right, double rotate, double power) {
            double leftFrontPower = -forward - right + rotate;
            double rightFrontPower = -forward + right - rotate;
        double rightRearPower = -forward - right - rotate;
        double leftRearPower = -forward + right + rotate;
        double maxPower = power;

        maxPower = Math.max(maxPower, Math.abs(leftFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightRearPower));
        maxPower = Math.max(maxPower, Math.abs(leftRearPower));

        lf.setPower(leftFrontPower / maxPower);
        rf.setPower(-rightFrontPower / maxPower);
        rb.setPower(-1 * -rightRearPower / maxPower);            //-- omdat gears
        lb.setPower(leftRearPower / maxPower);
    }

    public void drive1(float fl, float fr, float br, float bl, double weight) {
        lf.setPower(fl / weight);
        rf.setPower(fr / weight);
        rb.setPower(br / weight);
        lb.setPower(bl / weight);
    }
}

