package org.inv3r53.hw;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.monitor.*;
import org.apache.log4j.Logger;

public class FileWatchDog1 extends Thread {

    final static Logger logger = Logger.getLogger(FileWatchDog1.class);

    private String[] args;

    FileWatchDog1(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {

        FileAlterationMonitor monitor = new FileAlterationMonitor(Long.parseLong(args[1]));
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {

            @Override
            public void onFileCreate(File file) {
                try {

                    logger.info(getClass() + "File created: " + file.getCanonicalPath());
                } catch (IOException e) {
                    logger.error("x", e);
                }
            }

            @Override
            public void onFileDelete(File file) {
                try {

                    logger.info(getClass() + "File removed: " + file.getCanonicalPath());

                } catch (IOException e) {
                    logger.error("x", e);
                }
            }
        };

        String[] folders = args[0].split(";");
        for (String dir : folders) {
            FileAlterationObserver observer = new FileAlterationObserver(dir);
            observer.addListener(listener);
            monitor.addObserver(observer);
        }

        try {
            monitor.start();
        } catch (Exception e) {
            logger.error("x", e);
        }
    }
}
