package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp",group="LinearOpMode")
public class TeleOpNew extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //initializes all motor variables
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor glyphArm;

    //intializes all servos
    private Servo l1Glyph;
    private Servo l2Glyph;
    private Servo r1Glyph;
    private Servo r2Glyph;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        // Initializes motor variables
        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        glyphArm = hardwareMap.dcMotor.get("glyphArm");

        // Initializes servo variables
        l1Glyph = hardwareMap.servo.get("l1Glyph");
        l2Glyph = hardwareMap.servo.get("l2Glyph");
        r1Glyph = hardwareMap.servo.get("r1Glyph");
        r2Glyph = hardwareMap.servo.get("r2Glyph");

        // Initializes touch sensor variable

        // GlyphArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reverses the motor that runs backwards
        l2Glyph.setDirection(Servo.Direction.REVERSE);




        // Declares motor power variables
        rightDrive.setDirection(DcMotor.Direction.REVERSE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");



        // Initializes servo position
        double lGlyph = 0.6;
        double rGlyph = 0.6;

        l1Glyph.setPosition(0.5);
        l2Glyph.setPosition(0.5);
        r1Glyph.setPosition(0.5);
        r2Glyph.setPosition(0.5);

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Declares motor power variables
        double lPower, rPower, glPower;

        double lGlyph, rGlyph;

        // Variables for glyph manipulator servos

        if (gamepad1.y){
            l1Glyph.setPosition(0.5);
            l2Glyph.setPosition(0.5);
            r1Glyph.setPosition(0.5);
            r2Glyph.setPosition(0.5);
        }
        if (gamepad1.x){
            l1Glyph.setPosition(0.0);
            l2Glyph.setPosition(0.0);
            r1Glyph.setPosition(1.0);
            r2Glyph.setPosition(0.0);
        }



        // Sets lPower to trigger values
        lPower = ((gamepad1.left_trigger - gamepad1.right_trigger) + gamepad1.left_stick_x);
        rPower = ((gamepad1.left_trigger - gamepad1.right_trigger) - gamepad1.left_stick_x);

        // Sets glyph manipulator arm power to right stick
        glPower = gamepad1.right_stick_y;

        // Sets motors to motor power variables
        leftDrive.setPower(lPower);
        rightDrive.setPower(rPower);
        glyphArm.setPower(glPower);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
