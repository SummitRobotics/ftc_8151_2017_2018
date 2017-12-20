// FTC- 8151
// TeleOp Drive Code
// Jamey Luckett --- Aidan Beery


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOp",group="LinearOpMode")
public class TeleOp extends LinearOpMode {

    //Declares Hardware variables
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor leftDrive;
        private DcMotor rightDrive;
        private DcMotor glyphArm;
        private Servo l1Glyph;
        private Servo l2Glyph;
        private Servo r1Glyph;
        private Servo r2Glyph;
        private TouchSensor buttonUp;
        private TouchSensor buttonDown;

    //Declares motor power and servo position variables
    double tPower, lPower, rPower, glPwr, lGlyph, rGlyph;
//Runs Op Mode
    @Override
    public void runOpMode() {
        //Ties all hardware variables to the hardware map (assigns them to the configuration)
            leftDrive = hardwareMap.dcMotor.get("leftDrive");
            rightDrive = hardwareMap.dcMotor.get("rightDrive");
            glyphArm = hardwareMap.dcMotor.get("glyphArm");
            l1Glyph = hardwareMap.servo.get("l1Glyph");
            l2Glyph = hardwareMap.servo.get("l2Glyph");
            r1Glyph = hardwareMap.servo.get("r1Glyph");
            r2Glyph = hardwareMap.servo.get("r2Glyph");
            buttonUp = hardwareMap.touchSensor.get("buttonUp");
            buttonDown = hardwareMap.touchSensor.get("buttonDown");

        // reverses motor direction of one side
            leftDrive.setDirection(DcMotor.Direction.REVERSE);
        //initializes servo position
        //todo - test servo positions with new servos
            lGlyph = 0.6;
            rGlyph = 0.6;

            l1Glyph.setPosition(lGlyph);
            l2Glyph.setPosition(lGlyph);
            r1Glyph.setPosition(rGlyph);
            r2Glyph.setPosition(rGlyph);

        //todo - test servo direction
        l2Glyph.setDirection(Servo.Direction.REVERSE);
        r1Glyph.setDirection(Servo.Direction.REVERSE);

        //waits for bot to be started and starts timer
        waitForStart();
        runtime.reset();

        //iterative teleop loop, aka commands for driving the bot
        while(opModeIsActive())
        {

            //todo - adds status to telemetry console

            //initializes turning power
            tPower = gamepad1.left_stick_x; //todo- check if this is redundant

            //cuts tspeed in half if triggers are pressed
            //todo - what the fuck - test to see if this actually does anything
            if (gamepad1.left_trigger != 0|| gamepad1.right_trigger != 0){
                tPower = gamepad1.left_stick_x / 2;
            }else{
                tPower = gamepad1.left_stick_x;
            }

            //todo - test/fix servo positions
                if (gamepad1.y){
                    lGlyph = 0.0;
                    rGlyph = 0.0;
                }
                if (gamepad1.b){
                    lGlyph = 0.5;
                    rGlyph = 0.5;
                }
            //cuts glyph arm power when buttons are pressed - to use buttons as limits
            if (buttonUp.isPressed() && glPwr > 0) {
                glPwr = 0;
            }
            if (buttonDown.isPressed() && glPwr < 0) {
                glPwr = 0 ;
            }

            //sets lpower to trigger values and tspeed
            lPower = ((gamepad1.left_trigger - gamepad1.right_trigger) - gamepad1.left_stick_x);
            rPower = ((gamepad1.left_trigger - gamepad1.right_trigger) + gamepad1.left_stick_x);
            //sets glyph arm power to right stick up/down
            glPwr = gamepad1.right_stick_y;

            //Ties power/position variables to hardware variable power functions.

                glyphArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                // test if drive motors should be on break or float
                leftDrive.setPower(lPower);
                rightDrive.setPower(rPower);
                glyphArm.setPower(glPwr);

                l1Glyph.setPosition(lGlyph);
                l2Glyph.setPosition(lGlyph);
                r1Glyph.setPosition(rGlyph);
                r2Glyph.setPosition(rGlyph);

            //todo - telemetry
            telemetry.addData("Game Time", runtime);
            telemetry.update();
        }
    }

}
