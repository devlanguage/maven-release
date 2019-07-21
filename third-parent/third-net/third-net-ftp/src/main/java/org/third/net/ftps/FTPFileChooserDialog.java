
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.basic.common.util.NetworkUtil;
import org.basic.gui.swing.common.IsmBaseJDialog;
import org.third.transport.RemoteServerInfo;

public class FTPFileChooserDialog extends IsmBaseJDialog {
    private static final long serialVersionUID = -3203389781488981196L;
    public static String MANAGE_DB = "Select a Directory Path to Backup/Restore DB";
    public static String EXPORT_CALL_HISTORY = "Select a Directory Path to export Call History";
    public static String DOWNLD_SW = "Select a File Path to Download Software";
    public static String CERTIFICATE_DOWNLD = "Select a File Path to Download Certificate";
    public static String DB_BACKUP = "Select a Directory Path to Backup DB";
    private boolean _inhFTPServer = false;
    private boolean _dirOnly = false;
    private FileTableModel filesModel = null;
    private JTable _table = null;
    private JScrollPane scrollPane = null;
    private JTextField _jTextField_currentDirectory = null;
    private JComboBox<String> _jComboBox_filter = null;
    private JComboBox<String> jCombocddirBox = new JComboBox<String>();
    private JLabel jLabelcdDir = new JLabel();

    private Filter files = new Filter();
    private String _selectedPath = null;
    private List<String> _fileNames = null;
    private String curFilter = "*.*";
    private FTPClient ftpClient = null;
    private Component parentPanel;
    private boolean isForPatchFile = false;
    RemoteServerInfo server = null;

    public FTPFileChooserDialog(RemoteServerInfo server, boolean dirOnly, List<String> fileNames, boolean inhFTPServer) {
        _dirOnly = dirOnly;
        _fileNames = fileNames;
        _inhFTPServer = inhFTPServer;
        this.server=server;

        try {
            super.initializeDialog();
            jbInit();

            this.setTitle(this.DOWNLD_SW);
            connectFTPClient("/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectFTPClient(String dirPath) throws Exception {
        try {
            ftpClientLogin();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        try {
            String path = convertDirPath(dirPath);
            if (path != null) {
                ftpClient.changeWorkingDirectory(path);
            }
            files.setFiles(getListOfFiles());
            _jTextField_currentDirectory.setText(ftpClient.printWorkingDirectory());
        } catch (Exception ex) {
            ex.printStackTrace();

            String path = convertDirPath("");
            if (path != null) {
                ftpClient.changeWorkingDirectory(path);
            }
            files.setFiles(getListOfFiles());
            _jTextField_currentDirectory.setText(ftpClient.printWorkingDirectory());
        }
    }

    private void ftpClientLogin() throws Exception {
        ftpClient = new FTPClient();
        if (server.getIpAddr().contains(":")) {
            ftpClient.connect(Inet6Address.getByName(server.getIpAddr()), server.getPort());
        } else {
            ftpClient.connect(server.getIpAddr(), server.getPort());
        }

        // t71mr00100658
        // CS:can not change sw file directory on FTP server for read time out
        ftpClient.setDataTimeout(10000);
        if (ftpClient.isConnected()) {
            boolean connState = ftpClient.login(server.getUserName(), server.getPwd());
            if (!connState) {
                throw new Exception("FTP client login failed.");
            }
        } else {
            System.out.println("!!!!!!!!!!! FTP client is not connected");
            throw new Exception("FTP client is not connected");
        }
        ftpClient.enterLocalActiveMode();
        if (server.getIpAddr().contains(":")) {
            String[] ipAddress = NetworkUtil.getAllLocalHostIP();
            if (ipAddress != null) {
                for (int i = 0; i < ipAddress.length; i++) {
                    if (ipAddress[i].contains(":") && !ipAddress[i].toLowerCase().startsWith("fe80")) {
                        String[] array1 = ipAddress[i].split(":");
                        String[] array2 = server.getIpAddr().split(":");
                        if (array1[0].toLowerCase().equals(array2[0].toLowerCase())) {
                            ftpClient.setActiveExternalIPAddress(ipAddress[i]);
                            break;
                        }
                    }
                }
            }
        }
        ftpClient.setListHiddenFiles(true);
    }

    private void ftpClientLogout() throws Exception {
        ftpClient.quit();
    }

    // public FTPFile[] getListOfFiles() throws Exception
    // {
    // String currDir = ftpClient.pwd();
    // FTPFile[] files = ftpClient.dirDetails(currDir);
    // return files;
    // }
    //

    public FTPFile[] getListOfFiles() {

        try {
            String currDir = ftpClient.printWorkingDirectory();
            if (currDir.equals("/")) {
                currDir = null;
            }
            FTPFile[] files = ftpClient.listFiles(currDir);
            return files;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    protected void jbInit() throws Exception {

        JPanel pnl = new JPanel(new GridBagLayout());
        int row = 0;
        String text = server.getUserName() + "@ftp://" + server.getIpAddr() + ":" + server.getPort();
        JPanel dirPnl = new JPanel();
        dirPnl.setLayout(new BoxLayout(dirPnl, BoxLayout.X_AXIS));
        _jTextField_currentDirectory = new JTextField() {
            {
                setEditable(false);
            }
        };
        _jTextField_currentDirectory.setPreferredSize(new Dimension(220, 22));

        if (_inhFTPServer) {
            jLabelcdDir.setLabelFor(jCombocddirBox);
            jLabelcdDir.setText("Drive:");
            jLabelcdDir.setPreferredSize(new Dimension(60, 22));
            jCombocddirBox.setPreferredSize(new Dimension(50, 22));
            String pathName = "\\";
            File fileObject = new File(pathName);
            // String rootpath = configValueManager.getInstance().getConfigValue("TLAB_HOME");
            String rootpath = "/";
            String rootDrive = rootpath.substring(0, (rootpath.indexOf("\\") + 1));
            File[] driveLetters = fileObject.listRoots();

            for (int i = 0; i < driveLetters.length; i++) {
                jCombocddirBox.addItem(driveLetters[i].toString());
                if (driveLetters[i].toString().equalsIgnoreCase(rootDrive))
                    jCombocddirBox.setSelectedIndex(i);
            }

            dirPnl.add(jLabelcdDir);
            dirPnl.add(jCombocddirBox);

        }

        dirPnl.add(new JLabel(" Directory: "));
        dirPnl.add(_jTextField_currentDirectory);
        URL url_up = FTPFileChooserDialog.class.getResource("/images/app/up_dir.gif");
        URL url_new = FTPFileChooserDialog.class.getResource("/images/app/new_dir.gif");
        Icon up_folder = new ImageIcon(url_up);
        Icon new_folder = new ImageIcon(url_new);
        JButton upButton = new JButton(up_folder);
        JButton mkdirBtn = new JButton(new_folder);
        upButton.setToolTipText("Up One Level");
        mkdirBtn.setToolTipText("Create New Folder");
        upButton.setPreferredSize(new Dimension(28, 21));
        mkdirBtn.setPreferredSize(new Dimension(28, 21));
        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FTPFileChooserDialog.this.changeDirectory("..");
            }
        });
        mkdirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newfoder = "/NewFolder";
                if (_jTextField_currentDirectory.getText().endsWith("/"))
                    newfoder = "NewFolder";
                String dirname = _jTextField_currentDirectory.getText() + newfoder;
                String mesg = " Enter a new folder or directory Name:\n";
                dirname = (String) JOptionPane.showInputDialog(null, mesg, " Create a New Folder or Directory", -1, null, null, dirname);
                if (dirname != null) {
                    boolean success = createNewDirectory(dirname);
                    if (success) {
                        try {
                            _jTextField_currentDirectory.setText(dirname);
                            files.setFiles(null);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            showError("Failed to list files in the newly created folder" + dirname);
                        }
                        // Manager.getManagerFrame().showInformation("Creating the folder " + dirname +
                        // "\nhas been completed successfully.");
                    } else {
                        showError("Failed to create the folder" + dirname);
                    }
                }
            }
        });
        jCombocddirBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String driveName = jCombocddirBox.getSelectedItem().toString();
                if (driveName != null) {
                    boolean success = changeDrive("\\" + driveName);
                    if (success) {
                        files.setFiles(getListOfFiles());
                        // Manager.getManagerFrame().showInformation("Changing the Drive to " + driveName +
                        // "\nhas been completed successfully.");
                    } else {
                        // Since we are unable to change drive, setting the file list to empty.
                        files.setFiles(new FTPFile[0]);
                        showError("Unable to change to Drive : " + driveName);
                    }
                }
            }
        });

        dirPnl.add(upButton);
        dirPnl.add(mkdirBtn);
        pnl.add(new JTextField(text) {
            {
                setEditable(false);
            }
        },
                new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 2, 5),
                        1, 1));
        row++;
        if (_inhFTPServer) {
            JLabel homeDirLabel = new JLabel("Ftp Home Directory: ");
            pnl.add(homeDirLabel, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 5, 2, 5), 1, 1));
            row++;
        }
        pnl.add(dirPnl, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2,
                5, 2, 5), 1, 1));
        JPanel filterPnl = new JPanel(new BorderLayout());
        _jComboBox_filter = new JComboBox();
        _jComboBox_filter.setEditable(true);
        _jComboBox_filter.addItem("*.*");
        _jComboBox_filter.addItem("*.dcr");
        _jComboBox_filter.addItem("*.tlf");
        _jComboBox_filter.addItem("*.CURRENT");
        _jComboBox_filter.addItem("*.BACKUP");
        // for CPE SW
        _jComboBox_filter.addItem("*.cramfs");
        filterPnl.add(new JLabel("Filter:        "), BorderLayout.WEST);
        filterPnl.add(_jComboBox_filter, BorderLayout.CENTER);

        // if (!_dirOnly) {
        row++;
        pnl.add(filterPnl, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
                2, 5, 2, 5), 1, 1));
        // }

        filesModel = new FileTableModel();
        _table = new JTable(filesModel);
        _table.setDefaultRenderer(String.class, new FTPFileTableCellRenderer());
        // if (fileSelector.getSelectorType() == FileChooserType.TYPE_SW_DOWNLD) {
        // _table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // } else {
        _table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // }
        _table.addMouseListener(new TableClicker());
        TableColumn column_0 = _table.getColumnModel().getColumn(0);
        column_0.setPreferredWidth(180);
        TableColumn column_1 = _table.getColumnModel().getColumn(1);
        column_1.setPreferredWidth(30);
        scrollPane = new JScrollPane(_table);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        row++;

        pnl.add(scrollPane, new GridBagConstraints(0, row, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5,
                5, 5, 5), 1, 1));

        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().add(
                pnl,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 1,
                        1));

        _jComboBox_filter.addItemListener(files);

        curFilter = "*.*";
        _jComboBox_filter.setSelectedItem(curFilter);

    }

    private boolean createNewDirectory(String dirname) {
        boolean success = false;
        try {
            ftpClient.makeDirectory(dirname);
            ftpClient.changeWorkingDirectory(dirname);
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public void dispose() {
        try {
            ftpClientLogout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.dispose();
        parentPanel.requestFocus();

    }

    protected void onOkay() {

        String targetFileName = "";

        int[] rows = _table.getSelectedRows();
        if (rows.length != 2) {
            JOptionPane.showMessageDialog(this, "Please select a DCR and a ZIP file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FTPFile[] files = new FTPFile[2];
        files[0] = ((FileTableModel) _table.getModel()).getFileAt(rows[0]);
        files[1] = ((FileTableModel) _table.getModel()).getFileAt(rows[1]);

        try {
            _selectedPath = ftpClient.printWorkingDirectory();

            String fileName = "";
            if (_table.getSelectedRowCount() > 0) {
                FTPFile ftpFile = ((FileTableModel) _table.getModel()).getFileAt(_table.getSelectedRow());
                fileName = ftpFile.getName();
                _selectedPath = _selectedPath + "/" + fileName;
            }

            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String convertDirPath(String dirPath) {
        if (dirPath != null && dirPath.length() > 0) {
            String filePath = dirPath;
            if (filePath.indexOf("\\") > -1) {
                filePath = filePath.replaceAll("\\", "/");
            }
            if (filePath.indexOf(".dcr") > -1 || filePath.indexOf(".CURRENT") >= 0 || filePath.indexOf(".BACKUP") >= 0) {
                filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            }
            if (filePath.length() > 1 && filePath.charAt(1) == ':') {
                filePath = "/" + filePath;
            }
            if (filePath.charAt(filePath.length() - 1) != '/') {
                filePath = filePath + "/";
            }
            return filePath;
        } else {
            return null;
        }
    }

    protected void onCommandButton(String actionCommandName) {
        if (actionCommandName.equals(this.OK_COMMAND)) {
            this.onOkay();
        }
    }

    public String getSelectedFile() {
        _selectedPath = _jTextField_currentDirectory.getText();
        if (_selectedPath == null) {
            return "";
        } else if (_jComboBox_filter.getSelectedItem().toString().endsWith("*.dcr")) {
            int index = _selectedPath.lastIndexOf(".dcr");
            return _selectedPath.substring(0, index);
        }
        return _selectedPath;
    }

    public String getSelectedDirectory() {
        _selectedPath = _jTextField_currentDirectory.getText();
        if (_selectedPath == null) {
            return "";
        }
        return _selectedPath + "/";
    }

    public boolean changeDir(String directory) {
        boolean success = false;
        try {
            ftpClient.changeWorkingDirectory(directory);
            files.setFiles(getListOfFiles());
            filesModel.fireTableDataChanged();

            _jTextField_currentDirectory.setText(ftpClient.printWorkingDirectory());
            success = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to change director to " + directory, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return success;
    }

    private void changeDirectory(String directory) {

        try {
            ftpClient.changeWorkingDirectory(directory);
            files.setFiles(getListOfFiles());
            filesModel.fireTableDataChanged();
            _jTextField_currentDirectory.setText(ftpClient.printWorkingDirectory());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to change director to " + directory, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean changeDrive(String driveName) {
        boolean success = false;
        try {
            // ftpClient.chdir(driveName);
            // this.changeDirectory(driveName);
            ftpClient.changeWorkingDirectory("\\");
            System.out.println("Changing Drive to \\");
            ftpClient.changeWorkingDirectory(driveName);
            System.out.println("Changing Drive to \\" + driveName);
            files.setFiles(getListOfFiles());
            filesModel.fireTableDataChanged();
            _jTextField_currentDirectory.setText(ftpClient.printWorkingDirectory());
            success = true;
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
        return success;
    }

    public void showDialog(Component parent) {
        setSize(new Dimension(530, 475));
        setLocationRelativeTo(parent);
        parentPanel = parent;

        setVisible(true);

    }

    private class TableClicker extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if ((e.getModifiers() == MouseEvent.BUTTON1_MASK) && (e.getClickCount() == 2)) {
                // double click
                JTable table = (JTable) e.getSource();
                int row = table.rowAtPoint(e.getPoint());
                FTPFile file = ((FileTableModel) table.getModel()).getFileAt(row);
                if (file.isDirectory()) {
                    FTPFileChooserDialog.this.changeDirectory(file.getName());
                }
            }
        }
    }

    private class FileTableModel extends AbstractTableModel {
        String names[] = { "File Name", "Size", "Last Modified" };

        public Class getColumnClass(int col) {
            switch (col) {
                case 0:
                    return String.class;
                case 1:
                    return Long.class;
                case 2:
                    return Date.class;
            }
            return String.class;
        }

        public int getColumnCount() {
            return names.length;
        }

        public int getRowCount() {
            return files.getLength();
        }

        public Object getValueAt(int row, int col) {
            Object item;
            switch (col) {
                case 0:
                    item = ((FTPFile) files.getItem(row)).getName();
                    break;

                case 1:
                    item = new Long(files.getItem(row).getSize());
                    break;

                case 2:
                    item = files.getItem(row).getTimestamp().getTime();
                    break;

                default:
                    item = "";
            }
            return item;
        }

        public String getColumnName(int column) {
            return names[column];
        }

        public FTPFile getFileAt(int row) {
            return files.getItem(row);
        }

        public void fireTableDataChanged() {
            super.fireTableDataChanged();
            if (_table.getRowCount() >= 1) {
                _table.changeSelection(0, 0, false, false);
            }
        }
    }

    private class Filter implements ItemListener {
        private String _filter = curFilter;
        private FTPFile[] _files = new FTPFile[0];
        private FTPFile[] _filteredFiles = new FTPFile[0];

        public void applyFilter(String filter) {
            if (filter == null) {
                return;
            }
            _filter = filter;
            String fileType = filter.substring(1);
            if (!filter.startsWith("*")) {
                ArrayList newList = new ArrayList();
                for (int i = 0; i < _files.length; i++) {
                    if (_files[i].isDirectory()) {
                        newList.add(_files[i]);
                    }
                }
                _filteredFiles = new FTPFile[newList.size()];
                newList.toArray(_filteredFiles);
            } else {
                ArrayList newList = new ArrayList();
                for (int i = 0; i < _files.length; i++) {
                    String name = _files[i].getName();
                    if (filter.equals("*.*") || name.endsWith(fileType) || _files[i].isDirectory()) {
                        newList.add(_files[i]);
                    }
                }
                _filteredFiles = new FTPFile[newList.size()];
                newList.toArray(_filteredFiles);
            }
            filesModel.fireTableDataChanged();

            // _table.changeSelection(0, 0, false, false);
        }

        public void setFiles(FTPFile[] files) {
            _files = files;
            if (files == null) {
                _filteredFiles = new FTPFile[0];
                filesModel.fireTableDataChanged();
            } else {
                applyFilter(curFilter);
            }
        }

        public FTPFile[] getFiles() {
            return _filteredFiles;
        }

        public int getLength() {
            return _filteredFiles.length;
        }

        public FTPFile getItem(int index) {
            return _filteredFiles[index];
        }

        //
        // ItemListener
        //
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                curFilter = e.getItem().toString();
                applyFilter(curFilter);
                _jComboBox_filter.setSelectedItem(curFilter);
            }
        }
    }

    private class FTPFileTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 0 && value instanceof String) {
                FTPFile file = ((FileTableModel) table.getModel()).getFileAt(row);
                if (file != null && file.isDirectory()) {
                    ((JLabel) component).setText("[" + value.toString() + "]");
                }
            }

            return component;
        }
    }

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        try {
            client.connect(InetAddress.getByName("172.29.132.102"), 21);
            boolean status = client.login("bbb", "bbb");
            if (!status) {
                client.logout();
            }

            // client.enterLocalActiveMode();
            // client.setActiveExternalIPAddress("2001::1");

            // client.eprt(Inet6Address.getByName("2001::1"), 21);
            // client.nlst();
            // System.out.print(client.getReplyString());
            FTPFile[] list = client.listFiles();
            if (list != null && list.length > 0) {
                System.out.println(list.length);
            }

            // client.setFileType(FTP.BINARY_FILE_TYPE);
            // File file = new File("D:\\ftpTmp\\testFrom.txt");
            // InputStream input = new FileInputStream(file);
            // boolean flag = client.storeFile("testTo.txt", input);
            // if(flag) {
            // System.out.println(flag);
            // }
            // input.close();
            //
            // FTPListParseEngine engine = client.initiateListParsing("/");
            //
            // while (engine.hasNext()) {
            // FTPFile[] files = engine.getNext(25); // "page size" you want
            // if (files != null && files.length > 0) {
            // System.out.println(files.length);
            // }
            // }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setForPatchFile(boolean isForPatchFile) {
        this.isForPatchFile = isForPatchFile;
    }
}
