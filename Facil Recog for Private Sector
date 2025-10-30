import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Cameras {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        int selectedIndex = -1;

        // Buscar camaras conectadas (maximo 5)
        for (int i = 0; i < 5; i++) {
            VideoCapture cam = new VideoCapture(i);
            if (cam.isOpened()) {
                System.out.println("Camara detectada en indice: " + i);
                if (i > 0 && selectedIndex == -1) {
                    selectedIndex = i; // elegir primera externa
                }
                cam.release();
            } else {
                break;
            }
        }

        // Si no hay externa, usar integrada
        if (selectedIndex == -1) {
            selectedIndex = 0;
            System.out.println("Usando camara integrada (indice 0)");
        } else {
            System.out.println("Usando camara externa (indice " + selectedIndex + ")");
        }

        // Abrir camara
        VideoCapture camera = new VideoCapture(selectedIndex);
        if (!camera.isOpened()) {
            System.out.println("No se pudo abrir la camara.");
            return;
        }

        Mat frame = new Mat();
        System.out.println("Camara abierta. Presiona ESC para salir.");

        while (true) {
            if (camera.read(frame)) {
                Imgproc.putText(frame, "Camara #" + selectedIndex,
                        new org.opencv.core.Point(10, 30),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 1.0,
                        new org.opencv.core.Scalar(0, 255, 0), 2);
                HighGui.imshow("Vista de la camara", frame);

                // Salir con ESC
                if (HighGui.waitKey(1) == 27) {
                    break;
                }
            } else {
                System.out.println("No se pudo leer imagen de la camara.");
                break;
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
    }
}
