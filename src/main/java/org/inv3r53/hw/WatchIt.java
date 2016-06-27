package org.inv3r53.hw;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class WatchIt {
    final static Logger logger = Logger.getLogger(WatchIt.class);

    public static void main(String[] args) throws Exception {

        if (args == null || args.length < 2) {
            logger.info("invalid parameters!");
            System.exit(1);
        }
        new FileWatchDog1(args).start();
        new FileWatchDog2(args).start();

        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            logger.error("error", e);
        }
    }

}
