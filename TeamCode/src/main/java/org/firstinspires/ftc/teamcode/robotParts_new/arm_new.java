package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class arm_new {
    private DcMotorEx arm;
    double armPos;
    public void initArm(HardwareMap map) {
        arm = map.get(DcMotorEx.class, "arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void rotate (double power){
        armPos = arm.getCurrentPosition();
        arm.setPower(power);
    }
}
