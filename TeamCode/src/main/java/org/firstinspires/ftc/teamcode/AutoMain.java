package org.firstinspires.ftc.teamcode;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name = "Autonomous", group = "OpMode")
public class AutoMain extends LinearOpMode {
    // Initilization of Hardware
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor glyphArm;

    private Servo l1Glyph;
    private Servo l2Glyph;
    private Servo r1Glyph;
    private Servo r2Glyph;
    private Servo jewelArm;

    VuforiaLocalizer vuforia;
    int boxKey;

    private JewelDetector jewelDetector = null;

    //encoder values
    static double encoderTicks = 1440;
    static double wheelDiamater = 4.0;
    static double encoderInch = encoderTicks / wheelDiamater;

    public void runOpMode() {

        //Hardware init
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        glyphArm = hardwareMap.get(DcMotor.class, "glyphArm");
        l1Glyph = hardwareMap.servo.get("l1Glyph");
        l2Glyph = hardwareMap.servo.get("l2Glyph");
        r1Glyph = hardwareMap.servo.get("r1Glyph");
        r2Glyph = hardwareMap.servo.get("r2Glyph");
        jewelArm = hardwareMap.get(Servo.class, "jewelArm");


        //Vuforia init
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        //vuforia licence key -- todo: get licence key
        parameters.vuforiaLicenseKey = "";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");


        //DogeCV init
        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

        //Jewel Detector Settings
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        //jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

        jewelDetector.enable();


        //Encoder and Motor init
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets encoders to go to position
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Servo init
        l2Glyph.setDirection(Servo.Direction.REVERSE);

        l1Glyph.setPosition(0.5);
        l2Glyph.setPosition(0.5);
        r1Glyph.setPosition(0.5);
        r2Glyph.setPosition(0.5);

        //todo - test jewel init position
        jewelArm.setPosition(0.6);


        waitForStart();
        runtime.reset();
        //GAME START
        double tSpeed = 0.6;
        double fwdSpeed = 0.8;

        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("Vumark", "Is Visible");
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    telemetry.addData("Vumark Position: ", "Left");
                    boxKey = 1;
                } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    telemetry.addData("Vumark Position: ", "Right");
                    boxKey = 2;
                } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    telemetry.addData("Vumark Position: ", "Center");
                    boxKey = 3;
                }
            } else {
                telemetry.addData("Vumark", "Is Not Visible");
            }
            telemetry.update();


            String jewelPosition = jewelDetector.getCurrentOrder().toString();

            if (jewelPosition.equals("BLUE_RED")) {
                //turn to jewel position
                encoderDrive(tSpeed, 3, -3, 0.5);
                encoderDrive(tSpeed, -3, 3, 0.5);

            } else if (jewelPosition.equals("RED_BLUE")) {
                //turn to knock jewel
                encoderDrive(tSpeed, 3, -3, 0.5);
                encoderDrive(tSpeed, -3, 3, 0.5);
            }
            //recenter jewel arm
            jewelArm.setPosition(0.6);

            //cryptobox key paths

            //Left key
            if (boxKey == 1) {
                encoderDrive(fwdSpeed, 25, 25, 1.0);
                encoderDrive(tSpeed, 5, -5, 2.0);
                encoderDrive(fwdSpeed, 35, 35, 2.0);
            //Right Key
            } else if (boxKey == 2) {
                encoderDrive(fwdSpeed, 21, 21, 1.0);
                encoderDrive(tSpeed, 5, -5, 2.0);
                encoderDrive(fwdSpeed, 35, 35, 2.0);

             //Center key
            } else if (boxKey == 3) {
                encoderDrive(fwdSpeed, 23, 23, 1.0);
                encoderDrive(tSpeed, 5, -5, 2.0);
                encoderDrive(fwdSpeed, 35, 35, 2.0);
            }

        }
        l1Glyph.setPosition(0.0);
        l2Glyph.setPosition(0.0);
        r1Glyph.setPosition(1.0);
        r2Glyph.setPosition(0.0);

        telemetry.addData("Path", "Complete");
    }

    public void encoderDrive(double speed, double leftInch,
                             double rightInch, double stopTime) {
        int lTarget, rTarget;
        if (opModeIsActive()) {
            lTarget = leftDrive.getCurrentPosition() + (int) (leftInch * encoderInch);
            rTarget = rightDrive.getCurrentPosition() + (int) (rightInch * encoderInch);
            leftDrive.setTargetPosition(lTarget);
            rightDrive.setTargetPosition(rTarget);

            runtime.reset();
            leftDrive.setPower(Math.abs(speed));
            rightDrive.setPower(Math.abs(speed));


            while (opModeIsActive() &&
                    (runtime.seconds() < stopTime) &&
                    (leftDrive.isBusy() && rightDrive.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", lTarget, rTarget);
                //telemetry.addData("Path2", "Running at %7d :%7d",
                //        leftDrive.getCurrentPosition(),
                //       rightDrive.getCurrentPosition());
                //telemetry.update();
            }

            // Stop all motion;
            leftDrive.setPower(0);
            rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
