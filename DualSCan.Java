import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;

public class DualScan {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceDetector = new CascadeClassifier("resources/haarcascade_frontalface_default.xml");

        VideoCapture cam1 = new VideoCapture(0); // Laptop cam
        VideoCapture cam2 = new VideoCapture(1); // External cam

        if (!cam1.isOpened() && !cam2.isOpened()) {
            System.out.println("Alert: No Camera Detected");
            return;
        }

        Mat frame1 = new Mat();
        Mat frame2 = new Mat();

        boolean saved1 = false;
        boolean saved2 = false;

        System.out.println("System Active - Auto save when face is detected");

        while (true) {
            if (cam1.isOpened()) {
                cam1.read(frame1);
                if (!frame1.empty()) {
                    saved1 = detectAndDisplay(frame1, faceDetector, "Camera 1 - Laptop", "Recon_cam1.png", saved1);
                }
            }

            if (cam2.isOpened()) {
                cam2.read(frame2);
                if (!frame2.empty()) {
                    saved2 = detectAndDisplay(frame2, faceDetector, "Camera 2 - External", "Recon_cam2.png", saved2);
                }
            }

            int key = HighGui.waitKey(30);
            if (key == 27) break; // ESC to exit
        }

        cam1.release();
        cam2.release();
        HighGui.destroyAllWindows();
    }

    private static boolean detectAndDisplay(Mat frame, CascadeClassifier faceDetector, String windowName, String saveName, boolean alreadySaved) {
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(gray, faces);

        boolean faceDetected = false;

        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(frame, rect, new Scalar(0, 255, 0), 2);
            Imgproc.putText(frame, "Scanning for Verification...",
                    new Point(rect.x, rect.y - 10),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.8,
                    new Scalar(0, 255, 0), 2);
            faceDetected = true;
        }
 
        HighGui.imshow(windowName, frame);

        if (faceDetected && !alreadySaved) {
            Imgcodecs.imwrite(saveName, frame);
            System.out.println("Saved image: " + saveName);
            return true;
        }

        return alreadySaved;
    }
}
