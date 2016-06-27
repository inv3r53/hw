package org.inv3r53.hw;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.log4j.Logger;

public class FileWatchDog2 extends Thread {
    final static Logger logger = Logger.getLogger(FileWatchDog2.class);
    private String[] args;

    FileWatchDog2(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {

        FileSystemManager fsManager = null;
        try {
            fsManager = VFS.getManager();
        } catch (FileSystemException e1) {
            logger.error("error", e1);
        }
        DefaultFileMonitor fm = new DefaultFileMonitor(new FileListener() {

            public void fileDeleted(FileChangeEvent event) throws Exception {
                logger.info("----FILE DELETED-----: " + event.getFile().getURL());

            }

            public void fileCreated(FileChangeEvent event) throws Exception {
                logger.info("----FILE CREATED-----: " + event.getFile().getURL() + " isContentOpen=" + event.getFile().isContentOpen() + " isAttached=" + event.getFile().isAttached());

            }

            public void fileChanged(FileChangeEvent event) throws Exception {
                logger.info("----FILE MODIFIED-----: " + event.getFile().getURL());

            }
        });

        String[] folders = args[0].split(";");
        for (String dir : folders) {
            FileObject listendir = null;
            try {
                listendir = fsManager.resolveFile(dir);
                fm.addFile(listendir);
            } catch (FileSystemException e1) {
                logger.error("error", e1);
            }

        }
        fm.setDelay(Long.parseLong(args[1]));
        fm.setRecursive(true);
        fm.start();

    }
}
