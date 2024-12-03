package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_Two_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;

@TeleOp(name = "STAdrive_best", group = "TeleOp")
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
        double ms;
        double ms_difference = 0;
        boolean toggleBakje;
        boolean toggleClaw;
        boolean bakejeMovementAllowed = true;
        boolean clawMovementAllowed = true;
        boolean clawRotationAllowed = true;
        boolean slidesInputAllowed1 = true;
        boolean slidesInputAllowed2 = true;
        boolean armInputAllowed = true;
        waitForStart();
        if (isStopRequested()) return;
        double bakjeServoPos = 1;
        double clawServopos = 0;
        double servoRotation = 0.5;
        double trueSlidespower = 0;
        double trueArmPower = 0;
        ms = runtime.milliseconds() - ms_difference;
        while (opModeIsActive()) {                                  //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = 0.6 * gamepad1.right_stick_x;
            double speed = 4 - 3 * gamepad1.right_trigger;
            drivetrain.drive(-x, -y, -rotate, speed);
            toggleBakje = gamepad2.right_bumper;
            toggleClaw = gamepad2.left_bumper;
            double podX = parts.posX();
            double podY = parts.posY();


            if (toggleBakje && bakejeMovementAllowed) {
                bakjeServoPos = -bakjeServoPos + 1; //switch tussen 1 en 0
                bakejeMovementAllowed = false;
            }
            if (!toggleBakje) {
                bakejeMovementAllowed = true;
            }

            if (toggleClaw && clawMovementAllowed) {
                clawServopos = -clawServopos + 1; //switch tussen 1 en 0
                clawMovementAllowed = false;
            }
            if (!toggleClaw) {
                clawMovementAllowed = true;
            }

            if ((gamepad2.left_trigger > 0) && (servoRotation < 1) && clawRotationAllowed) {
                servoRotation += 0.25;
                clawRotationAllowed = false;

            }
            if ((gamepad2.right_trigger > 0) && (servoRotation > 0) && clawRotationAllowed) {
                servoRotation -= 0.25;
                clawRotationAllowed = false;
            }
            if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
                clawRotationAllowed = true;
            }
            double armPower = -gamepad2.left_stick_y;
            trueArmPower = 0;
            if (((parts.getArmPos() <= 1000) || (armPower <= 0)) && ((parts.getArmPos() >= 0) || (armPower >= 0))) {
                trueArmPower = armPower;
            }
            double armPos1 = 0;
            double armPos2 = 1000;
            if (gamepad2.a) {
                if (armPos1 < parts.getArmPos()) {
                    trueArmPower = -0.9;
                } else {
                    trueArmPower = 0.9;
                }
            }
            if ((gamepad2.b) && (!gamepad2.a)) {
                if (armPos2 < parts.getArmPos()) {
                    trueSlidespower = -0.9;
                } else {
                    trueSlidespower = 0.9;
                }
                slidesInputAllowed2 = false;
            }


            /*if ((gamepad2.left_trigger > 0)&&(servoRotation<1)) {
                servoRotation += 0.03;
            }
            if ((gamepad2.right_trigger > 0)&&(servoRotation>0)) {
                servoRotation -= 0.03;
            }*/

            parts.servoRotation(servoRotation);
            parts.sampleBakje(bakjeServoPos);
            parts.intakeClaw(clawServopos);

            parts.setArmPower(trueArmPower);

            double slidespower = -gamepad2.right_stick_y;

            if (((slidesInputAllowed1) && (slidesInputAllowed2)) || gamepad2.back) {
                trueSlidespower = 0;
                if ((((parts.getSlidesPos() <= 3750) || (slidespower <= 0)) && ((parts.getSlidesPos() >= 0) || (slidespower >= 0))) || gamepad2.back) {
                    trueSlidespower = slidespower;
                }
                slidesInputAllowed1 = true;
                slidesInputAllowed2 = true;
            }
            //nieuw vanaf hier
            double slidePos1 = 0;
            double slidePos2 = 3500;
            double slidePosVariation = 50;
            if (Math.abs(parts.getSlidesPos() - slidePos1) > slidePosVariation) {
                if ((gamepad2.x) && (slidesInputAllowed2)) {
                    // zodat
                    if (slidePos1 < parts.getSlidesPos()) {
                        trueSlidespower = -0.9;
                    } else {
                        trueSlidespower = 0.9;

                    }
                    slidesInputAllowed1 = false;
                }

            } else {
                slidesInputAllowed1 = true;
            }

            if (Math.abs(parts.getSlidesPos() - slidePos2) > slidePosVariation) {
                if ((gamepad2.y) && (!gamepad2.x) && (slidesInputAllowed1)) {
                    // zodat
                    if (slidePos2 < parts.getSlidesPos()) {
                        trueSlidespower = -0.9;
                    } else {
                        trueSlidespower = 0.9;
                    }
                    slidesInputAllowed2 = false;
                }

            }
            if (Math.abs(parts.getSlidesPos() - slidePos2) > slidePosVariation) {
                if ((gamepad2.a) && (armInputAllowed)) {
                    armInputAllowed = false;
                }
            } else {
                armInputAllowed = true;
            }
            double rotation = parts.posY() - parts.posY2();
            parts.setSlidesPower(trueSlidespower);
            telemetry.addData("slidesPos", parts.getSlidesPos());
            telemetry.addData("rotation", rotation);
            telemetry.addData("servoRotatePos", servoRotation);
            telemetry.update();

            /*
            parts.rollerIntake((gamepad2.right_trigger * 1.5) - (gamepad2.left_trigger * 1.5)); // voor toggle
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
                if (bakjeServoPos<1){
                    bakjeServoPos += 0.03;
                }
                onderdelen.servo0(bakjeServoPos);
            } else if (servo0_off) {
                if (bakjeServoPos > 0) {
                    bakjeServoPos -= 0.03;
                }
                onderdelen.servo0(bakjeServoPos);

            }
            telemetry.addData("Servopos", bakjeServoPos);
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
