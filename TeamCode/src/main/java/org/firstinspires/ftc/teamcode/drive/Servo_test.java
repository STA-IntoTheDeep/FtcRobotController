package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;


    @TeleOp(name = "Servo_test", group = "TeleOp")
//Naam van project
    public class Servo_test extends LinearOpMode {
        private ElapsedTime runtime = new ElapsedTime();                                //Slaat op hoe lang de robot is geinitialiseerd

        Drivetrain_new drivetrain = new Drivetrain_new();

        All_Parts parts = new All_Parts();                                      //Roept de onderdelen aan uit de geïmporteerde map

        @Override
        public void runOpMode() throws InterruptedException {
            drivetrain.init(hardwareMap);
            parts.init(hardwareMap);


            waitForStart();
            if (isStopRequested()) return;
            double bakjeServoPos = 1;
            double clawServopos = 0;
            double intakeRotation = 0;
            double intakeOrientation = 0;


            while (opModeIsActive()) {                                  //Loop van het rijden van de robot
                if(gamepad1.x) {
                    bakjeServoPos = 0;
                } else if (gamepad1.y){
                    bakjeServoPos = 1;
                }

                if(gamepad1.a) {
                    clawServopos = 0;
                } else if (gamepad1.b){
                    clawServopos = 1;
                }

                if(gamepad1.dpad_up) {
                    intakeOrientation = 0;
                } else if (gamepad1.dpad_left){
                    intakeOrientation = 1;
                }

                if(gamepad1.dpad_right) {
                    intakeRotation = 0;
                } else if (gamepad1.dpad_down){
                    intakeRotation = 1;
                }

                parts.servoRotation(intakeRotation);
                parts.sampleBakje(bakjeServoPos);
                parts.intakeClaw(clawServopos);
                parts.setIntakeOrientation(intakeOrientation);




                double rotation = parts.posY() - parts.posY2();

                telemetry.addData("slidesPos", parts.getSlidesPos());
                telemetry.addData("rotation", rotation);
                telemetry.addData("servoRotatePos", intakeRotation);
                telemetry.update();
            }
        }
    }
