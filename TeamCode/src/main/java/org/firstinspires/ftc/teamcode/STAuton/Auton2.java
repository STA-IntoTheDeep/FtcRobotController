package org.firstinspires.ftc.teamcode.STAuton;

import java.util.Arrays;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;

class StageHandler{
    All_Parts parts;
    Auton2 auto;
    private long ittCount=0;

    public static void main(){
        //constructor. normaal niet gebruiken
    }
    void init(All_Parts partsArg, Auton2 aut){
        this.parts=partsArg;
        this.auto=aut;
    }
    boolean hb(){
        ittCount++;
        return true;
    }

    void test(){
        new Thread(() -> {
            parts.drive0(5,0,0,100);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            auto.ready("test");
        }).start();
    }
    void fin(){
        new Thread(() -> {
            parts.drive0(0,0,0,1);
            auto.ready("fin");
        }).start();
    }
}

@Autonomous(name = "Auton2", group = "Autonomous")
//Naam van project
public class Auton2 extends LinearOpMode {

    StageHandler sh;
    All_Parts apac;

    private int stInt=0;
    private String curStage="";
    private String[] que={"test"};


    private boolean isReady=true;
    void ready(String stage){
        if(curStage==stage)this.isReady=true;
    };
    /*void stage(){

    }*/

    @Override
    public void runOpMode() throws InterruptedException {
        //start
        // parts
        apac.init(hardwareMap);
        while(true){
            //loop
            telemetry.addData("stage=",curStage);
            telemetry.addData("stages left =",que.length-stInt);
            telemetry.addData("ready=",isReady);
            if(sh.hb()&&isReady){
                if(stInt>=que.length){
                    stInt=0;
                    curStage="final";
                }
                else curStage=que[stInt++];
                switch (curStage){
                    case"test":
                        sh.test();
                        break;

                    case"final":
                        sh.fin();
                        break;
                }
            }
        }
    }
}
