package org.basic.common.util;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import org.basic.common.bean.CommonConstants;
import org.basic.common.bean.CommonLogger;

public class ConfigFileMonitor implements Runnable {

    private long lastModifiedTime;
    private int fileUpdateCheckTime;
    private AtomicBoolean running = new AtomicBoolean();
    private File monitoredFile = null;
    CommonLogger logger = CommonLogger.getLogger(ConfigFileMonitor.class);

    public ConfigFileMonitor(String filePath) {

        monitoredFile = new File(filePath);
        lastModifiedTime = monitoredFile.lastModified();
        fileUpdateCheckTime = Integer.parseInt(CommonConstants.SERVER_FILE_MODIFY_CHECK_INTERVAL);
        running.set(true);
    }

    public void run() {

        while (running.get()) {
            try {
                Thread.sleep(fileUpdateCheckTime);
            } catch (InterruptedException e) {
                logger.log(CommonLogger.SEVERE, "run", "", e);
            }
            if (monitoredFile.lastModified() > lastModifiedTime) {
                lastModifiedTime = monitoredFile.lastModified();
                ConfigurationManager.reloadCommonConfiguration();
            }
        }
    }

    public void stop() {

        running.set(false);
    }
};
