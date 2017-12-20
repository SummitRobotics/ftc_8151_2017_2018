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
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //todo - test ^

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
                leftDrive.setTargetPosition((int) encoderInch);
                rightDrive.setTargetPosition((int) encoderInch);
                leftDrive.setPower(tSpeed);
                rightDrive.setPower(-tSpeed);

                leftDrive.setTargetPosition((int) encoderInch);
                rightDrive.setTargetPosition((int) encoderInch);
                leftDrive.setPower(-tSpeed);
                rightDrive.setPower(tSpeed);

            } else if (jewelPosition.equals("RED_BLUE")) {
                //turn to knock jewel
                leftDrive.setTargetPosition((int) encoderInch);
                rightDrive.setTargetPosition((int) encoderInch);
                leftDrive.setPower(-tSpeed);
                rightDrive.setPower(tSpeed);

                //return to start position
                leftDrive.setTargetPosition((int) encoderInch);
                rightDrive.setTargetPosition((int) encoderInch);
                leftDrive.setPower(-tSpeed);
                rightDrive.setPower(tSpeed);
            }


        }


        // Initialization of Hardware Values
        //Initialization of Motor Encoders
        //Initialization of Servos


        //Start Game
        //Grab Glyph
        //Scan VuMark
        //Push vumark position to telemetry
        //Use DogeCV to detect jewel
        //Lower servo arm
        //Turn based on DogeCV value

        //Set encoders to run to positions based on value of VuMark
        //Lower Arm
        //Drop Glyph
        telemetry.addData("Path", "Complete");
    }
}
