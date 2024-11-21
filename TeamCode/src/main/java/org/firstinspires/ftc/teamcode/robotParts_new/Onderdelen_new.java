package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


public class Onderdelen_new {
    private Servo servo0;
    private CRServo servo1;
    public void init(HardwareMap map) {
        servo0 = map.get(Servo.class, "servo0");
        servo1 = map.get(CRServo.class,  "servo1");
    }
    public void servo0(double direction){
        servo0.setPosition(direction);
    }
    public void servo1(double power){servo1.setPower(power);}


}
