// FTC- 8151
// Time-Based Autonomous to drive forwards into side Cryptobox and deposit a glyph.
// Jamey Luckett --- Aidan Beery

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="TimeDrive - Side Cryptobox", group="LinearOpMode")

public class AutoTime1 extends LinearOpMode {

    // Declares all hardware variables
        private ElapsedTime     runtime = new ElapsedTime();
        private DcMotor leftDrive;
        private DcMotor rightDrive;
        private DcMotor glyphArm;
        private Servo l1Glyph;
        private Servo l2Glyph;
        private Servo r1Glyph;
        private Servo r2Glyph;
        private Servo jewelArm;

    // Declares speed variables
        static final double     FORWARD_SPEED = 0.8;
        static final double     TURN_SPEED    = 0.5;
//Runs Op Mode
    @Override
    public void runOpMode() {

        // Ties all hardware variables to the hardware map (assigns them to the configuration)
            leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
            rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
            glyphArm = hardwareMap.get(DcMotor.class, "glyphArm");
            jewelArm = hardwareMap.get(Servo.class, "jewelArm");
            l1Glyph = hardwareMap.servo.get("l1Glyph");
            l2Glyph = hardwareMap.servo.get("l2Glyph");
            r1Glyph = hardwareMap.servo.get("r1Glyph");
            r2Glyph = hardwareMap.servo.get("r2Glyph");


        //reverses motor direction
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        //Initializes servo positions
            jewelArm.setPosition(0.6);
            l1Glyph.setPosition(0.5);
            l2Glyph.setPosition(0.3);
            r1Glyph.setPosition(0.5);
            r2Glyph.setPosition(0.3);

        // Wait for the game to start and starts the timer.
        waitForStart();
        runtime.reset();

        //Loop to drive forward for 3 seconds.
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            leftDrive.setPower(FORWARD_SPEED);
            rightDrive.setPower(FORWARD_SPEED);
        }
        //Drops glyph in Cryptobox after driving
        if (opModeIsActive() && runtime.seconds() > 3.1){
            l1Glyph.setPosition(0.8);
            l2Glyph.setPosition(0.8);
            r1Glyph.setPosition(0.8);
            r2Glyph.setPosition(0.8);
        }
        // Tells driver station that autonomous has completed.
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}