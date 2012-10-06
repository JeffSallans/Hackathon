/**
 * programming contest dufus
 */
package com.barracuda;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.Math;
import java.util.List;
import java.util.Map;

public class ContestServer {
    private static Log log = LogFactory.getLog(ContestServer.class);

    public Boolean ping() {
        log.info("ping");
        return true;
    }

    public int init_game(Map state) {
        log.info("init_game");
        log.info(state.toString());
        return 0;
    }

    public Integer get_bid(List<Integer> offer, Map state) {
        log.info("get_bid");
        log.info(offer.toString());
        return new Integer(4);
    }

    public Integer make_choice(List<Integer> offer, Map state) {
        log.info("make_choice");
        return offer.get(0);
    }

    public int move_result(Map result) {
        log.info("move_result");
        log.info(result.toString());
        return 0;
    }

    public int game_result(Map result) {
        log.info("game_result");
        log.info(result.toString());
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int port = 8888;
        WebServer webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.setVoidMethodEnabled(true);

        // class key = null for no prefix on method names
        phm.addHandler(null, ContestServer.class);
        xmlRpcServer.setHandlerMapping(phm);

        log.info("starting XML-RPC server at /RPC2 on port " + port);
        webServer.start();
        
    }
}
