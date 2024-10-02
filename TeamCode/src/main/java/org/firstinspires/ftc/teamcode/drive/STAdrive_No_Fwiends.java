package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;

@TeleOp(name = "STAdrive_No_fwiends", group = "TeleOp")
//Naam van project
public class STAdrive_No_Fwiends extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

    Drivetrain_new drivetrain = new Drivetrain_new();
    arm_new arm = new arm_new();

    Onderdelen_new onderdelen = new Onderdelen_new();                               //Roept de onderdelen aan uit de geïmporteerde map

    @Override
    public void runOpMode() {
        drivetrain.init(hardwareMap);
        arm.initArm(hardwareMap);
        onderdelen.init(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;
        double servopos = 0;

        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = -0.75 * gamepad1.right_stick_x;
            double spinny = (0.75 * gamepad1.right_stick_y);
            /*if (rotate != 0) {
                telemetry.addLine("chippi chippi chappa chappa");
            } else {
                telemetry.addLine("No chippi chippi chappa chappa D:");
            }*/

            //Zet zin op het scherm
            //telemetry.addData("Verstreken tijd", getRuntime());     //Zet data op het scherm
            //telemetry.addData("armPos", arm.arm.getCurrentPosition());
            telemetry.update();
            //Zorgt dat data geüpdated blijft
            // max position arm is 7000
            // min position arm is 2738

            double shpeeed;

            boolean maxpowerdecrease = gamepad1.right_bumper;


            if (maxpowerdecrease) {
                shpeeed = 5;
            } else {
                shpeeed = 1;
            }
            drivetrain.drive(x, y, rotate, shpeeed);                         //Voert bij drivetrain aangemaakte opdracht uit
            arm.rotate(spinny);

            double servo0_on = gamepad1.right_trigger;                         //Koppelt servobeweging aan variabele
            double servo0_off = gamepad1.left_trigger;

            //Koppelt servobeweging aan variabele
            //boolean servo2grijpnaarbinnen = gamepad1.right_bumper;
            //boolean servo2grijpnaarbuiten = gamepad1.left_bumper;

            if (servo0_on > 0.0420) {
                servopos += 0.003;
                onderdelen.servo0(servopos);
            } else if (servo0_off > 0.0420) {
                if (servopos > 0) {
                    servopos -= 0.003;
                }
                onderdelen.servo0(servopos);

            }
            telemetry.addData("Servopos", servopos);
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
