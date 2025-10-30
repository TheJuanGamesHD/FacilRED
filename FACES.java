import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui; 

public class FACES {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceDetector = new CascadeClassifier("resources/haarcascade_frontalface_default.xml");
        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Alert: Camera Cant Open");
            return;
        }

        Mat frame = new Mat();

        while (true) {
            if (camera.read(frame)) {
                Mat grayFrame = new Mat();
                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

                MatOfRect faces = new MatOfRect();
                faceDetector.detectMultiScale(grayFrame, faces);

                for (Rect rect : faces.toArray()) {
                    Imgproc.rectangle(frame, rect, new Scalar(0, 255, 0), 2);
                    Imgproc.putText(frame, "Scanning for Verification...", 
                        new Point(rect.x, rect.y - 10), 
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.8, 
                        new Scalar(0, 255, 0), 2);
                }

                HighGui.imshow("Camera ON - Counter Terroristm ", frame);

                if (HighGui.waitKey(1) == 32) { // 32 = barra espaciadora
                    Imgcodecs.imwrite("Recon_0.png", frame);
                    System.out.println("Saves as 'Recon_0.png'");
                    break;
                }
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
    }
}
