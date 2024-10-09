package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm_Two_new {
    private DcMotorEx arm2;
    double armPos2;
    public void initArm2(HardwareMap map) {
        arm2 = map.get(DcMotorEx.class, "arm2");
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void rotate2 (double power){
        armPos2 = arm2.getCurrentPosition();
        arm2.setPower(power);
    }

    public double getCurrentPos (){
        return arm2.getCurrentPosition();
    }
}
