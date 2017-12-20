package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name = "VuMarkSense", group = "LinearOpMode")
public class VuforiaVuMarkSense extends LinearOpMode{

    VuforiaLocalizer vuforia;

    public void runOpMode() {

        //Initializes vuforia localizer without enabling the camera monitor in telemetry
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        //vuforia licence key -- todo: get licence key
        parameters.vuforiaLicenseKey ="";
        //tells vuforia to use rear camera
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        //todo - create trackable for jewel

        waitForStart();
        relicTrackables.activate();

                while (opModeIsActive()){
                    RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                    if (vuMark != RelicRecoveryVuMark.UNKNOWN){
                        telemetry.addData("Vumark","Is Visible");
                        if(vuMark == RelicRecoveryVuMark.LEFT){
                            telemetry.addData("Vumark Position: ", "Left");
                        }
                        else if (vuMark == RelicRecoveryVuMark.RIGHT){
                            telemetry.addData("Vumark Position: ", "Right");
                        }
                        else if (vuMark == RelicRecoveryVuMark.CENTER){
                            telemetry.addData("Vumark Position: ", "Center");
                        }
                    }
                    else{
                        telemetry.addData("Vumark", "Is Not Visible");
                    }
                    telemetry.update();

        }

    }

}
