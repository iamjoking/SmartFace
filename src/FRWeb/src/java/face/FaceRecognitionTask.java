package face;

import java.awt.image.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jasper.tagplugins.jstl.core.Out;
import sun.misc.BASE64Encoder;
/**
 * This class will call local programs to do the work of Face Recognition
 * @author bsidb
 */
public class FaceRecognitionTask {
    private static String FaceDetectionProgramPath = "/home/bsidb/Yunio/document_laptop/work/face/SmartFace/src/FaceDetection/";
    private static String TmpDirPath = "/tmp/fr/";
    private int count = 1;
    
    private RenderedImage image = null;
    public FaceRecognitionTask(RenderedImage img){
        this.image = img;
    }
    /**
     * Do the recognition task
     * @return the HTML code that describe the recognition result
     */
    public String doRecognition() {
        
            String tmpDir = TmpDirPath + count + "/";
            String originFilePath = tmpDir + "origin.png";
            
        try {
            //Wrtie image to tmp file
            //Create Dirs that may be used
            new File(tmpDir + "faces").mkdirs();
            
            javax.imageio.ImageIO.write(image, "png", new File(originFilePath));
            
            //Try to call face detection
            System.out.println(FaceDetectionProgramPath  + "FaceDetection "+ " -i " + originFilePath
                    + " -d " + TmpDirPath + count + "/faces/ -x png -g -e -o " + tmpDir);
            Process p = Runtime.getRuntime().exec(FaceDetectionProgramPath  + "FaceDetection "+ " -i " + originFilePath
                    + " -d " + TmpDirPath + count + "/faces/ -x png -o " + tmpDir,null,new File(FaceDetectionProgramPath));
            //wait for the result
            p.waitFor();
            //Read result face
            File faceDir = new File(tmpDir + "faces/");
            File resultFile = new File(tmpDir + "origin.pos");
            if(resultFile.exists()){
                StringBuilder imageHTML = new StringBuilder();
                Scanner scanner = new Scanner(resultFile);
                int facecount = scanner.nextInt();
                if(facecount == 0){
                    return "I did not have a face in this image.";
                }
                for(int i = 0; i < facecount; i++){
                    File faceImageFile = new File(faceDir.getAbsolutePath() + "/origin_" + i + ".png");
                    if(!faceImageFile.exists()) continue;
                    RenderedImage faceImage = javax.imageio.ImageIO.read(faceImageFile);
                    //omit the faces that are too small
                    if(faceImage.getWidth()*faceImage.getHeight() < 0.001*image.getHeight()*image.getWidth()) continue;
                    String imgsrc = encodeImage(faceImage);
                    imageHTML.append(imgsrc);
                }
                return imageHTML.toString();
            }else{
                 return "Face Detection Program didn't produce any result.";
            }
        } catch (IOException ex) {
            Logger.getLogger(FaceRecognitionTask.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return ex.toString();
        } catch(InterruptedException e){
            return "Program is interrupted." + e.toString();
        }

    //    return "Not Implemented yet.";
    }
    
    /**
     * Encode the img to Base64 String
     * @param img
     * @return "data:image/png;base64,....."
     */
    private String encodeImage(RenderedImage faceImage){
                   //Encode the result
        BASE64Encoder encoder = new BASE64Encoder();
        java.io.ByteArrayOutputStream imgByte  =new ByteArrayOutputStream();
        String imgBase64;
        try {
            if(faceImage != null){
                javax.imageio.ImageIO.write(faceImage, "png", imgByte);
                imgBase64 = encoder.encode(imgByte.toByteArray());
            }else{
                return "I fail to detect a face";
            }
        } catch (IOException ex) {
            return "Error in recognition" + ex.toString();
        }
         return "<img src=\"data:image/png;base64," + imgBase64 + "\" />";
    }
    
}
