/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.basic.db.proxool.dbscript;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;

import java.sql.SQLException;

/**
 * Run a {@link Script script}.
 *
 * @version $Revision: 1.1 $, $Date: 2015/06/30 04:53:59 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: ygong $ (current maintainer)
 * @since Proxool 0.5
 */
public class ScriptRunner {

    private static final Log LOG = LogFactory.getLog(ScriptRunner.class);

    /**
     * Run the script.
     *
     * @param script to run
     * @param adapter so we know where to connections from
     * @throws SQLException if anything goes wrong
     */
    protected static final void runScript(Script script, ConnectionAdapterIF adapter, CommandFilterIF commandFilter) throws SQLException, ProxoolException {
        adapter.setup(script.getDriver(), script.getUrl(), script.getInfo());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

        Command[] commands = script.getCommands();
        for (int i = 0; i < commands.length; i++) {
            Command command = commands[i];
            long start = System.currentTimeMillis();

            // Execute the SQL
            Commander[] commanders = new Commander[command.getLoad()];
            for (int load = 0; load < command.getLoad(); load++) {
                commanders[load] = new Commander(adapter, command, commandFilter);
                Thread t = new Thread(commanders[load]);
                t.setName(script.getName() + "." + command.getName() + "." + load);
                t.start();
            }

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOG.error("Awoken from sleep", e);
                }

                int remaining = command.getLoad();
                for (int load = 0; load < command.getLoad(); load++) {
                    if (commanders[load].isFinished()) {
                        remaining--;
                    }
                }

                if (remaining > 0) {
                    // LOG.debug("Waiting for " + remaining + " threads to complete.");
                } else {
                    break;
                }
            }

            long elapsed = System.currentTimeMillis() - start;
            int count = command.getLoad() * command.getLoops();
            double lap = (double) elapsed / (double) count;
            if (count > 1) {
                LOG.info(adapter.getName() + ":" + command.getName() + " ran " + count + " commands in " + elapsed + " milliseconds (avg." + lap + ")");
            } else {
                LOG.debug(adapter.getName() + ":" + command.getName() + " ran in " + elapsed + " milliseconds");
            }

        }
    }

}
