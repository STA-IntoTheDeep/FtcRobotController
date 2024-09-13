package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;
//import org.firstinspires.ftc.teamcode.robotParts_new.Slides_new;

@TeleOp(name = "STAdrive_new",group = "TeleOp")                                     //Naam van project
public class STAdrive_new extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

    //Slides_new slides = new Slides_new();
    Drivetrain_new drivetrain = new Drivetrain_new();                               //Roept de drivetrain aan uit de geïmporteerde map
    Onderdelen_new onderdelen = new Onderdelen_new();                               //Roept de onderdelen aan uit de geïmporteerde map

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);
        onderdelen.init(hardwareMap);
        //slides.init(hardwareMap);
        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = gamepad1.right_stick_x;
            //double maxPower;

            //boolean maxpowerdecrease = gamepad1.dpad_up;

            /*
            if (maxpowerdecrease) {
                maxPower = 2;
            } else {
                maxPower = 1;
            }*/
            drivetrain.drive(x, y, rotate);                         //Voert bij drivetrain aangemaakte opdracht uit

            //boolean servo0_and_1_on = gamepad1.a;                         //Koppelt servobeweging aan variabele
            //boolean servo0_and_1_off = gamepad1.b;                        //Koppelt servobeweging aan variabele
            //boolean left_and_right_slides_up = gamepad1.y;
            //boolean left_and_right_slides_down = gamepad1.x;
            //boolean servo2grijpnaarbinnen = gamepad1.right_bumper;
            //boolean servo2grijpnaarbuiten = gamepad1.left_bumper;
            //double slides_movement = 0;

            /*if (servo0_and_1_on) {
                onderdelen.servo0(0.75);
                onderdelen.servo1(0.75);
            } else if (servo0_and_1_off) {
                onderdelen.servo0(0);
                onderdelen.servo1(0);
            }*/
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
        */}



            //telemetry.addLine();                                    //Zet zin op het scherm
            //telemetry.addData("Verstreken tijd", getRuntime());     //Zet data op het scherm
            //telemetry.update();                                     //Zorgt dat data geüpdated blijft
        }
    }
