package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class partcount {
    int servos = 1;
}


public class All_Parts {

    private Servo servo0;
    int posyOffset;
    private DcMotorEx lf;
    private DcMotorEx rf;
    private DcMotorEx lb;
    private DcMotorEx rb;
    public DcMotorEx slides;
    private DcMotorEx arm;
    //private DcMotorEx arm;
    //private DcMotorEx arm2;
    //private CRServo rollerintake;
    private Servo sampleBakje;
    private Servo intakeClaw;
    private Servo servoRotation;
    private Servo intakeOrientation;
    int slidePosDisplacement;
    int armPosDisplacement;

    partcount c = new partcount();
    Servo[] servo = {servo0};

    public void init(HardwareMap map) {
        /*
        for (int a = 0; a <= (c.servos-1); a++) {
            servo[a] = map.get(Servo.class, "servo" + a);
        }
         */
        slides = map.get(DcMotorEx.class, "slides");
        //rollerintake = map.get(CRServo.class, "intake");
        sampleBakje = map.get(Servo.class, "bakje");
        intakeClaw = map.get(Servo.class, "Claw");
        intakeOrientation = map.get(Servo.class,"intakeTurn");
        servoRotation = map.get(Servo.class, "servoRotation");
        lf = map.get(DcMotorEx.class, "left_front");
        rf = map.get(DcMotorEx.class, "right_front");
        lb = map.get(DcMotorEx.class, "left_back");
        rb = map.get(DcMotorEx.class, "right_back");
        arm = map.get(DcMotorEx.class, "arm");

        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slidePosDisplacement = slides.getCurrentPosition();
        armPosDisplacement = arm.getCurrentPosition();
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        posyOffset = lf.getCurrentPosition();
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

    public int posY() {
        return (lf.getCurrentPosition()-posyOffset);
    }

    public int posX() {
        return rf.getCurrentPosition();
    }
    public int posY2(){return lb.getCurrentPosition();}
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


    /*public void rollerIntake(double power) {
        rollerintake.setPower(power);
    }*/

    public void sampleBakje(double pos) {sampleBakje.setPosition(pos);}

    public void intakeClaw(double pos) {intakeClaw.setPosition(pos);}

    public void servoRotation(double pos) {servoRotation.setPosition(pos);}
    public void setIntakeOrientation(double pos) {intakeOrientation.setPosition(pos);}
    public void setSlidesPower(double power) {
        slides.setPower(power);
    }
    public void setSlidePosition(double pos){
        slides.setPower((Math.abs(slides.getCurrentPosition()-slidePosDisplacement-pos+500)-Math.abs(slides.getCurrentPosition()-slidePosDisplacement-pos-500))*-0.0009);

    }
    public double slidespower(){return slides.getPower();}
    public void setArmPower(double power) {
        arm.setPower(power);
    }

    public int getSlidesPos(){
        return slides.getCurrentPosition()-slidePosDisplacement;
    }
    public int getArmPos(){
        return arm.getCurrentPosition()-armPosDisplacement;
    }
}


