package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="AutoDrive",group="LinearOpMode")
public class AutoEncoder extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor glyphArm;

    private Servo l1Glyph;
    private Servo l2Glyph;
    private Servo r1Glyph;
    private Servo r2Glyph;
    private Servo jewelArm;

    double encoderTicks = 1440;
    double wheelDiamater = 4.0;
    double encoderInch = encoderTicks / wheelDiamater;

    public void runOpMode(){
        //Initializes hardware map
            leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
            rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
            glyphArm = hardwareMap.get(DcMotor.class, "glyphArm");
            l1Glyph = hardwareMap.servo.get("l1Glyph");
            l2Glyph = hardwareMap.servo.get("l2Glyph");
            r1Glyph = hardwareMap.servo.get("r1Glyph");
            r2Glyph = hardwareMap.servo.get("r2Glyph");
            jewelArm = hardwareMap.get(Servo.class, "jewelArm");

        //Initializes Encoders
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets encoders to go to position
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //todo - test ^
        waitForStart();

        double fwdSpeed = 0.8;
        double tSpeed = 0.6;

        //todo - test encoder pos
        //sets target to 5 inches at fwd power
        leftDrive.setTargetPosition(5 * (int)encoderInch);
        rightDrive.setTargetPosition(5 * (int)encoderInch);
        leftDrive.setPower(fwdSpeed);
        rightDrive.setPower(fwdSpeed);

        //if center
            //go fwd for x inches
            //turn
            //go fwd until cryptobox
        //else if left
            //go fwd for x inches
             //turn
            //go fwd until cryptobox







    }
}