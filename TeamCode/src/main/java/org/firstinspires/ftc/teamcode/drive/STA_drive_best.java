package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_Two_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;

@TeleOp(name = "STAdrive_new_reversed", group = "TeleOp")
//Naam van project
public class STA_drive_best extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

    Drivetrain_new drivetrain = new Drivetrain_new();
    Arm_new arm = new Arm_new();
    Arm_Two_new arm2 = new Arm_Two_new();
    Onderdelen_new onderdelen = new Onderdelen_new();
    All_Parts parts = new All_Parts();                                      //Roept de onderdelen aan uit de geïmporteerde map

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);
        parts.init(hardwareMap);
        //arm.initArm(hardwareMap);
        //onderdelen.init(hardwareMap);
        //arm2.initArm2(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;
        double servopos = 0;

        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = 0.6 * gamepad1.right_stick_x;
            drivetrain.drive(-x, -y, -rotate, 1);

            parts.rollerIntake(gamepad1.right_trigger);

            if (gamepad1.dpad_up){
               servopos += 3;
            } else if(gamepad1.dpad_down){
                servopos -= 3;
            }

            parts.sampleBakje(servopos);

            /*
            double arm1Velocity = -0.8 * gamepad2.left_stick_y;
            double arm2Velocity = 0.6 * gamepad2.right_stick_y;
            boolean armCalibration = gamepad2.x;
            double armDisplacement = 0;
            double armDisplacement2 = 0;
            boolean armFasterMovement = gamepad2.y;
            if (armFasterMovement){
                arm1Velocity *= 2;
                arm2Velocity *= 2;
            }
            //Zet zin op het scherm
            //telemetry.addData("Verstreken tijd", getRuntime());     //Zet data op het scherm
            //telemetry.addData("armPos", arm.getCurrentPos());

            //Zorgt dat data geüpdated blijft
            // max position arm is 7000
            // min position arm is 2738

            double speed = -2 * gamepad1.right_trigger + 3;

                                     //Voert bij drivetrain aangemaakte opdracht uit
            arm.rotate(arm1Velocity);
            arm2.rotate2(arm2Velocity);

            boolean servo0_on = gamepad2.left_bumper;                         //Koppelt servobeweging aan variabele
            boolean servo0_off = gamepad2.right_bumper;

            //Koppelt servobeweging aan variabele
            //boolean servo2grijpnaarbinnen = gamepad1.right_bumper;
            //boolean servo2grijpnaarbuiten = gamepad1.left_bumper;
            if (armCalibration) {
                armDisplacement = arm.getCurrentPos();
                armDisplacement2 = arm2.getCurrentPos();
            }
            double armPos = arm.getCurrentPos() - armDisplacement;
            double armPos2 = arm2.getCurrentPos() - armDisplacement2;

            if (servo0_on) {
                if (servopos<1){
                    servopos += 0.03;
                }
                onderdelen.servo0(servopos);
            } else if (servo0_off) {
                if (servopos > 0) {
                    servopos -= 0.03;
                }
                onderdelen.servo0(servopos);

            }
            telemetry.addData("Servopos", servopos);
            telemetry.addData("podpos_x", drivetrain.pos_x());
            telemetry.addData("podpos_y", drivetrain.pos_y());
            telemetry.addData("ArmPos", armPos);
            telemetry.addData("ArmPos2", armPos2);
            telemetry.addData("ArmPos without offset",arm.getCurrentPos());
            telemetry.addData("ArmPos2 without offset",arm2.getCurrentPos());
            telemetry.update();
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
