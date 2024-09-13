package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Onderdelen_new {
    private Servo servo0, servo1,servo2;
    public void init(HardwareMap map) {
        servo0 = map.get(Servo.class, "servo0");
        servo1 = map.get(Servo.class, "servo1");
        servo2 = map.get(Servo.class, "servo2");
    }
    /*public void servo0(Servo.Direction direction){servo0.setDirection(direction);}
    public void servo1(double position){servo1.setPosition(position);}
    public void servo1(Servo.Direction direction){servo1.setDirection(direction);}
    public void servo2(double position){servo2.setPosition(position);}
    public void Servo2(Servo.Direction direction){servo2.setDirection(direction);}
*/
}
