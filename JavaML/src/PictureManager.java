import piclab.Picture;
import piclab.Pixel;

import java.io.File;

/**
 * @author Michael Albert
 * @course CSS 143 B Prof. Kalmin
 * @school UW Bothell
 */
public class PictureManager {

    static double[] toDoubleArrayBright(Picture p){
        Pixel[][] pixels2D = p.getPixels2D();

        double[] out = new double[pixels2D.length * pixels2D[0].length];
        int index = 0;

        for (int i = 0; i < pixels2D.length; i++) {
            for (int j = 0; j < pixels2D[i].length; j++) {
                out[index] = pixels2D[i][j].getBright()/256.0;
                index++;
            }
        }
        return out;
    }

    public static void trainFromDir(String trainerName, File homeDir){


        File[] categories = homeDir.listFiles();
        int categoryCount = categories.length;

        for (int i = 0; i < categoryCount; i++) {
            double[] outputArray = new double[categoryCount];
            outputArray[i] = 1;

            File[] trainingFiles = homeDir.listFiles()[i].listFiles();
            if (trainingFiles == null){System.exit(0);}
            if (trainingFiles.length == 0){System.exit(0);}

            int file = 0;
            for (File f: trainingFiles) {
                System.out.println("Processing Picture " + (file++) + " out of " + trainingFiles.length + " in set {" + i + " of " + categoryCount + "}");
                Picture p = new Picture(f.getAbsolutePath());
                double[] pixelBrightArray = PictureManager.toDoubleArrayBright(p);
                Trainer t = new Trainer(64 * 64, 2);
                t.importFrom(new File(trainerName + ".trainer"));
                t.addSet(pixelBrightArray, outputArray);
                t.exportTo(new File(trainerName + ".trainer"));
            }
        }
    }
}
