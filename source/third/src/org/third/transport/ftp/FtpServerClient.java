
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import org.third.transport.RemoteServerInfo;

/**
 * <pre>
 *
 * </pre>
 */
public class FtpServerClient {
    public static void main(String[] args) {
        final JFrame f = new JFrame();
        RemoteServerInfo info = new RemoteServerInfo("sunshapp40", 21, "ygong", "tellabs1$", "/");
        List<String> fileNames = new ArrayList<String>();
        FTPFileChooserDialog fbd = new FTPFileChooserDialog(info, false, fileNames, true);

        JPanel mainPanel = new JPanel(new GridLayout());
        JButton okBtn = new JButton("Test");
        okBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fbd.setVisible(true);
            }
        });

        JButton closeBtn = new JButton("close");
        closeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fbd.dispose();
            }
        });
        
        mainPanel.add(okBtn);

        mainPanel.add(closeBtn);
        
        f.getContentPane().add(mainPanel);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
