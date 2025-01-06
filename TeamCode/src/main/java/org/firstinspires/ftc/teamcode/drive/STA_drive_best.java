package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
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
    //Arm_new arm = new Arm_new();
    //Arm_Two_new arm2 = new Arm_Two_new();
    //Onderdelen_new onderdelen = new Onderdelen_new();
    All_Parts parts = new All_Parts();                                      //Roept de onderdelen aan uit de geïmporteerde map
    BNO055IMU imu;
    Orientation angles;

    @Override
    public void runOpMode() throws InterruptedException {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class,  "imu");
        imu.initialize(parameters);
        drivetrain.init(hardwareMap);
        parts.init(hardwareMap);
        //arm.initArm(hardwareMap);
        //onderdelen.init(hardwareMap);
        //arm2.initArm2(hardwareMap);
        double ms;
        double ms_difference = runtime.milliseconds();
        boolean toggleSpeed;
        boolean toggleBakje;
        boolean toggleClaw;
        boolean toggleIntakeOrientation = true;
        boolean bakjeMovementAllowed = true;
        boolean clawMovementAllowed = true;
        boolean intakeOrientationMovementAllowed = true;
        boolean speedChangeAllowed = true;
        boolean clawRotationAllowed = true;
        boolean slidesInputAllowed1 = true;
        boolean slidesInputAllowed2 = true;
        boolean armInputAllowed1 = true;
        boolean armInputAllowed2 = true;
        boolean armInputAllowed = true;
        boolean toggleArmMoveA = false;
        boolean armMoveAllowedA = true;
        boolean armMovementA = false;
        boolean toggleArmMoveB = false;
        boolean armMoveAllowedB = true;
        boolean armMovementB = false;
        waitForStart();
        if (isStopRequested()) return;
        double bakjeServoPos = 0.95;
        double clawServopos = 0;
        double servoRotation = 0.5;
        double trueSlidespower = 0;
        double trueArmPower = 0;
        double intakeOrientation = 0;
        double speed = 0.6;
        while (opModeIsActive()) {                                   //Loop van het rijden van de robot
            double y = -gamepad1.left_stick_x;                       //Koppelt geactiveerde knop op controller aan variabele
            double x = gamepad1.left_stick_y;
            double rotate = 0.6 * gamepad1.right_stick_x;
            double armPower = 0.7 * -gamepad2.left_stick_y;
            double podX = parts.posX();
            double podY = parts.posY();
            double armPos1 = 300;
            double armPos2 = 950;
            double slidespower = -gamepad2.right_stick_y;
            double slidePos1 = 0;
            double slidePos2 = 3400;
            double slidePosVariation = 100;
            double rotation = parts.posY() - parts.posY2();
            ms = runtime.milliseconds() - ms_difference;
            toggleSpeed = gamepad1.b;
            toggleBakje = gamepad2.right_bumper;
            toggleClaw = gamepad2.left_bumper;
            toggleIntakeOrientation = gamepad2.dpad_up;
            toggleArmMoveA = gamepad2.a;
            toggleArmMoveB = gamepad2.b;

            //Toggle position of bakje if button is pressed (bakjemovementallowed makes sure one click only toggles once)
            if (toggleBakje && bakjeMovementAllowed) {
                if (bakjeServoPos == 0.5) {
                    bakjeServoPos = 0.95;
                } else if (bakjeServoPos == 0.95) {
                    bakjeServoPos = 0.5;
                }
                bakjeMovementAllowed = false;
            } else if (!toggleBakje) {
                bakjeMovementAllowed = true;
            }

            //Toggle speed if button is pressed (speedChangeAllowed makes sure one click only toggles once)
            if (toggleSpeed && speedChangeAllowed) {
                if (speed == 0.6) {
                    speed = 3;
                } else if (speed == 3) {
                    speed = 0.6;
                }
                speedChangeAllowed = false;
            } else if (!toggleSpeed) {
                speedChangeAllowed = true;
            }

            //Toggle claw position if button is pressed (clawMovementAllowed makes sure one click only toggles once)
            if (toggleClaw && clawMovementAllowed) {
                clawServopos = -clawServopos + 1; //switch tussen 1 en 0
                clawMovementAllowed = false;
            } else if (!toggleClaw) {
                clawMovementAllowed = true;
            }

            //Toggle intake orientation if button is pressed (intakeorientationmovementallowed makes sure one click only toggles once)
            if (toggleIntakeOrientation && intakeOrientationMovementAllowed) {
                intakeOrientation = -intakeOrientation + 0.7; //switch tussen 0.7 en 0
                intakeOrientationMovementAllowed = false;
            } else if (!toggleIntakeOrientation) {
                intakeOrientationMovementAllowed = true;
            }

            //Toggle claw orientation if button is pressed (claworientationmovementallowed makes sure one click only toggles once)
            if ((gamepad2.left_trigger > 0.5) && (servoRotation < 1) && clawRotationAllowed) {
                servoRotation += 0.25;
                clawRotationAllowed = false;
            } else if ((gamepad2.right_trigger > 0.5) && (servoRotation > 0) && clawRotationAllowed) {
                servoRotation -= 0.25;
                clawRotationAllowed = false;
            } else if (gamepad2.left_trigger <= 0.5 && gamepad2.right_trigger <= 0.5) {
                clawRotationAllowed = true;
            }

            //Makes controller1 able to control arm in case gamepad2 doesnt work anymore
            if ((armPower == 0) && (gamepad1.left_bumper)) {
                trueArmPower = -1;
            } else if ((armPower == 0) && (gamepad1.right_bumper)) {
                trueArmPower = 1;
            } else {
                trueArmPower = armPower;
            }

            //set limits for arm
            if ((((parts.getArmPos() <= 1050) || (armPower < 0)) && ((parts.getArmPos() >= 400) || (armPower > 0))) || gamepad2.back) {
                trueArmPower = armPower;
            }

            //toggle voor arm position 1
            if (toggleArmMoveA && armMoveAllowedA) {
                armMovementA = !armMovementA;
                armMoveAllowedA = false;
            } else if (!toggleArmMoveA) {
                armMoveAllowedA = true;
            }

            //move arm if allowed to drop position
            if (armMovementA) {
                intakeOrientation = 0.7;
                servoRotation = 0.75;
                if (armPos1 < parts.getArmPos()) {
                    trueArmPower = -1;
                    ms_difference = runtime.milliseconds();
                } else if (ms > 300){
                    clawServopos = 0;
                    armMovementA = false;
                }
            }

            //toggle voor arm position 2
            if (toggleArmMoveB && armMoveAllowedB) {
                armMovementB = !armMovementB;
                armMoveAllowedB = false;
            } else if (!toggleArmMoveB) {
                armMoveAllowedB = true;
            }

            //move arm if allowed to pickup position
            if ((armMovementB)) {
                intakeOrientation = 0;
                if (armPos2 > parts.getArmPos()) {
                    clawServopos = 0;
                    trueArmPower = 1;
                } else {
                    armMovementB = false;
                }
            }

            //set limits for slides
            if (((slidesInputAllowed1) && (slidesInputAllowed2)) || gamepad2.back) {
                trueSlidespower = 0;
                if ((((parts.getSlidesPos() <= 3400) || (slidespower <= 0)) && ((parts.getSlidesPos() >= -1000) || (slidespower >= 0))) || gamepad2.back) {
                    trueSlidespower = slidespower;
                }
                slidesInputAllowed1 = true;
                slidesInputAllowed2 = true;
            }

            //Move slides if input allowed
            if (Math.abs(parts.getSlidesPos() - slidePos1) > slidePosVariation) {
                if ((gamepad2.x) && (slidesInputAllowed1)) {
                    if (slidePos1 < parts.getSlidesPos()) {
                        trueSlidespower = -1;
                    } else {
                        trueSlidespower = 1;
                    }
                    slidesInputAllowed1 = false;
                }

            } else {
                slidesInputAllowed1 = true;
            }

            //move slides if allowed 2
            if (Math.abs(parts.getSlidesPos() - slidePos2) > slidePosVariation) {
                if ((gamepad2.y) && (!gamepad2.x) && (slidesInputAllowed2)) {
                    trueSlidespower = ((Math.abs(parts.getSlidesPos() - slidePos2 + 500) - Math.abs(parts.getSlidesPos() - slidePos2 - 500)) * -0.0009);
                    slidesInputAllowed2 = false;
                }

            } else {
                slidesInputAllowed2 = true;
            }

            parts.servoRotation(servoRotation);
            parts.sampleBakje(bakjeServoPos);
            parts.intakeClaw(clawServopos);
            parts.setIntakeOrientation(intakeOrientation);
            parts.setArmPower(trueArmPower);

            drivetrain.drive(-x, -y, -rotate, speed);

            parts.setSlidesPower(trueSlidespower);
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("direction", angles.firstAngle);
            //telemetry.addData("roll", angles.secondAngle);
            //telemetry.addData("pitch", angles.thirdAngle);
            telemetry.addData("slidesPos", parts.getSlidesPos());
            telemetry.addData("servoRotatePos", servoRotation);
            telemetry.addData("ypos", podY);
            telemetry.addData("xpos", podX);
            telemetry.addData("intakeOrientation", intakeOrientation);
            telemetry.addData("speed", speed);
            telemetry.addData("armpos", parts.getArmPos());
            telemetry.addData("Maxpower", drivetrain.getmaxpower());
            telemetry.update();
        }


    }
}
