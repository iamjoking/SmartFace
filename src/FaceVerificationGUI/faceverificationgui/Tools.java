package faceverificationgui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

class TJchooser {

    private javax.swing.JTextField jtfFiles;

    public TJchooser(JTextField jtf) {
        this.jtfFiles = jtf;
    }

    public String openFile(String path, int selectMode, boolean multiSelect) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(path));
            chooser.setFileSelectionMode(selectMode);
            chooser.setMultiSelectionEnabled(multiSelect);
            int operation = chooser.showOpenDialog(null);
            if (operation == JFileChooser.APPROVE_OPTION) {
                File[] files = chooser.getSelectedFiles();
                if(files.length == 0){
                    files = new File[1];
                    files[0] = chooser.getSelectedFile();
                }
                StringBuilder buffer = new StringBuilder();
                for (File file : files) {
                    if (buffer.length() != 0) {
                        buffer.append(";");
                    }
                    buffer.append(file.getPath());
                }
                if(jtfFiles != null){
                    jtfFiles.setText(buffer.toString());
                }
                return buffer.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}