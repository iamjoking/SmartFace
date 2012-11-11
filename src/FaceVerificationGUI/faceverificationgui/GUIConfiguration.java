/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faceverificationgui;

/**
 *
 * @author bsidb
 */
public class GUIConfiguration {
    protected java.util.Properties config = new java.util.Properties();
    
    public void set(String key,String value){
        config.setProperty(key, value);
    }
    
    public String generateCommand(){
        throw new AbstractMethodError("Not Support Yet");
    }
        
}
