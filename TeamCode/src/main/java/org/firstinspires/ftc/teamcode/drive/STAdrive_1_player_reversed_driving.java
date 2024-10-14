package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.STAuton.Sensors;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_Two_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;
@Disabled
@TeleOp(name = "STAdrive_1_player_reversed_driving",group = "TeleOp")                                     //Naam van project
public class STAdrive_1_player_reversed_driving extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

    Drivetrain_new drivetrain = new Drivetrain_new();
    Arm_new arm = new Arm_new();
    Arm_Two_new arm2 = new Arm_Two_new();
    Sensors sensor = new Sensors();

    Onderdelen_new onderdelen = new Onderdelen_new();                               //Roept de onderdelen aan uit de geïmporteerde map

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);
        arm.initArm(hardwareMap);
        onderdelen.init(hardwareMap);
        sensor.init(hardwareMap);
        arm2.initArm2(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;
        double servopos = 0;

        double armDisplacement = arm.getCurrentPos();
        double armDisplacement2 = arm2.getCurrentPos();

        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = 0.6 * gamepad1.right_stick_x;
            double armPowerDecrease = 3*gamepad1.left_trigger + 1;
            boolean armCalibration = gamepad1.x;
            double arm1Velocity;
            double arm2Velocity;
            boolean arm1RotateUp = gamepad1.dpad_up;
            boolean arm1RotateDown = gamepad1.dpad_down;
            boolean arm2RotateUp = gamepad1.dpad_right;
            boolean arm2RotateDown = gamepad1.dpad_left;
            boolean arm2TurnWithArm1 = gamepad1.b;
            if (arm2RotateUp) {
                arm2Velocity = 0.5 / armPowerDecrease;
            } else if (arm2RotateDown){
                arm2Velocity = -0.5/armPowerDecrease;
            } else{
                arm2Velocity = 0;
            }
            if (arm1RotateUp) {
                arm1Velocity = 0.5/ armPowerDecrease;
                if (arm2TurnWithArm1){
                    arm2Velocity = -0.25 / armPowerDecrease;
                }
            } else if (arm1RotateDown){
                arm1Velocity = -0.5/ armPowerDecrease;
                if (arm2TurnWithArm1){
                    arm2Velocity = -0.25 / armPowerDecrease;
                }
            } else{
                arm1Velocity = 0;
            }
            /*if (rotate != 0) {
                telemetry.addLine("chippi chippi chappa chappa");                       //dont question it
            } else {
                telemetry.addLine("No chippi chippi chappa chappa D:");
            }*/

            //Zet zin op het scherm
            //telemetry.addData("Verstreken tijd", getRuntime());     //Zet data op het scherm

            telemetry.update();
            //Zorgt dat data geüpdated blijft
            // max position arm is 7000
            // min position arm is 2738


            double speed = 3 * gamepad1.right_trigger + 1;

            drivetrain.drive(x, y, rotate, speed);                         //Voert bij drivetrain aangemaakte opdracht uit
            arm.rotate(arm1Velocity);
            arm2.rotate2(arm2Velocity);

            boolean servo0_on = gamepad1.right_bumper;                         //Koppelt servobeweging aan variabele
            boolean servo0_off = gamepad1.left_bumper;

            //Koppelt servobeweging aan variabele
            //boolean servo2grijpnaarbinnen = gamepad1.right_bumper;
            //boolean servo2grijpnaarbuiten = gamepad1.left_bumper;
            if (armCalibration){
                armDisplacement = arm.getCurrentPos();
                armDisplacement2 = arm2.getCurrentPos();
            }
            double armPos = arm.getCurrentPos() - armDisplacement;
            double armPos2 = arm2.getCurrentPos() - armDisplacement2;

            if (servo0_on) {
                servopos += 0.003;
                onderdelen.servo0(servopos);
            } else if (servo0_off) {
                if (servopos > 0) {
                    servopos -= 0.003;
                }
                onderdelen.servo0(servopos);

            }
            telemetry.addData("Servopos",servopos);
            telemetry.addData("ArmPos",armPos);
            telemetry.addData("ArmPos2",armPos2);
            telemetry.addData("ArmPos without offset",arm.getCurrentPos());
            telemetry.addData("ArmPos2 without offset",arm2.getCurrentPos());
            telemetry.addData("podpos_x",drivetrain.pos_x());
            telemetry.addData("podpos_y",drivetrain.pos_y());
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




        }
    }
