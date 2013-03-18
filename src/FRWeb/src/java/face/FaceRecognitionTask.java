package face;

import java.awt.image.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jasper.tagplugins.jstl.core.Out;
import sun.misc.BASE64Encoder;
/**
 * This class will call local programs to do the work of Face Recognition
 * @author bsidb
 */
public class FaceRecognitionTask {
    private RenderedImage image = null;
    public FaceRecognitionTask(RenderedImage img){
        this.image = img;
    }
    /**
     * Do the recognition task
     * @return the HTML code that describe the recognition result
     */
    public String doRecognition(){
        BASE64Encoder encoder = new BASE64Encoder();
        java.io.ByteArrayOutputStream imgByte  =new ByteArrayOutputStream();
        String imgBase64;
        try {
            if(image != null){
                javax.imageio.ImageIO.write(image, "png", imgByte);
                imgBase64 = encoder.encode(imgByte.toByteArray());
            }else{
                return "Format not support!";
            }
        } catch (IOException ex) {
            return "Error in recognition" + ex.toString();
        }
        return "<img src=\"data:image/png;base64," + imgBase64 + "\" />";
    //    return "Not Implemented yet.";
    }
    
}
