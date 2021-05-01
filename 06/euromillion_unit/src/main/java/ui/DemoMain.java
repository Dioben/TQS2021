package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;
import org.apache.logging.log4j.*;
public class DemoMain {

    /**
     * demonstrates a client for ramdom euromillions bets
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        
        // played sheet
        CuponEuromillions thisWeek = new CuponEuromillions();
        logger.info("Betting with three random bets...");
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());

        // simulate a random draw
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();

        //report results
        logger.info("You played:");
        String res = thisWeek.format();
        logger.info(res);

        logger.info("Draw results:");
        res = draw.getDrawResults().format();
        logger.info(res);

        logger.info("Your score:");
        for (Dip dip : draw.findMatches(thisWeek)) {
            String info = dip.format();
            logger.info(info);

        }
    }
}
