package org.iso8583;

import org.jpos.iso.*;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;

import java.io.IOException;
import java.io.InputStream;

public class MockISO8583Server implements ISORequestListener {

    private MapErrorCode mapErrorCode;

    public static void main(String[] args) throws ISOException {
        try (InputStream inputStream = MockISO8583Server.class.getResourceAsStream("/bankplusdef.xml")) {
            String hostname = "localhost";
            int portNumber = 5000;
            ISOPackager packager = new GenericPackager(inputStream);
            ServerChannel channel = new PostChannel(hostname, portNumber, packager);
            ISOServer server = new ISOServer(portNumber, channel, null);

            server.addISORequestListener(new MockISO8583Server());

            System.out.println("ISO8583 server started...");
            new Thread(server).start();
        } catch (IOException | ISOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean process(ISOSource isoSrc, ISOMsg isoMsg) {
        try {
            System.out.println("ISO8583 incoming message on host ["
                    + ((BaseChannel) isoSrc).getSocket().getInetAddress()
                    .getHostAddress() + "]");

            receiveMessage(isoSrc, isoMsg);
            logISOMsg(isoMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private void receiveMessage(ISOSource isoSrc, ISOMsg isoMsg)
            throws Exception {
        System.out.println("ISO8583 Message received...");
        MapErrorCode mapErrorCode = new MapErrorCode();
        //ISOMsg reply = new ISOMsg();
        //reply.setMTI("0410");
        isoSrc.send(mapErrorCode.map(isoMsg));
    }

    public static void logISOMsg(ISOMsg msg) {
        System.out.println("----ISO MESSAGE-----");
        try {
            System.out.println("  MTI : " + msg.getMTI());
            for (int i = 1; i <= msg.getMaxField(); i++) {
                if (msg.hasField(i)) {
                    System.out.println("    Field-" + i + " : "
                            + msg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("--------------------");
        }

    }

}
