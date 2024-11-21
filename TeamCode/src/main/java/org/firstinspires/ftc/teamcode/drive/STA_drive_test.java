package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_Two_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;

@TeleOp(name = "STA_drive_test", group = "TeleOp")
//Naam van project
public class STA_drive_test extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

    //Drivetrain_new drivetrain = new Drivetrain_new();
    //Arm_new arm = new Arm_new();
    //Arm_Two_new arm2 = new Arm_Two_new();
    Onderdelen_new onderdelen = new Onderdelen_new();
    //All_Parts parts = new All_Parts();                                      //Roept de onderdelen aan uit de geïmporteerde map

    @Override
    public void runOpMode() throws InterruptedException {
        //drivetrain.init(hardwareMap);
        //arm.initArm(hardwareMap);
        onderdelen.init(hardwareMap);
        //arm2.initArm2(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;
        double servopos = 0;
        double servopower;

        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
          servopower = gamepad1.left_stick_y;
          onderdelen.servo1(servopower);
            /*if (servo2grijpnaarbinnen) {
                onderdelen.servo2(0.85);
            } else if (servo2grijpnaarbuiten) {
                onderdelen.servo2(0.72);
            }
            if (left_and_right_slides_up){
                slides_movement = 0.6;
            } else if (left_and_right_slides_down) {
                slides_movement = -0.6;
            }
            else {
                slides_movement = 0;
            }
            slides.slides_go_brr(slides_movement);
        */
        }


    }
}
