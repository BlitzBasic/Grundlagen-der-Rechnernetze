package de.uulm.in.vs.grn.d1;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.UUID;

public class ServerConnectionImpl implements VoidRunnerBoard.ServerConnection {

    private VoidRunnerBoard voidRunnerBoard;
    private InetSocketAddress serverEndpoint;
    private DatagramSocket clientSocket;
    private UpdateHandler updateHandler;

    public ServerConnectionImpl(VoidRunnerBoard voidRunnerBoard, InetSocketAddress serverEndpoint) throws IOException {
        this.voidRunnerBoard = voidRunnerBoard;
        this.serverEndpoint = serverEndpoint;

        clientSocket = new DatagramSocket();

        updateHandler = new UpdateHandler(voidRunnerBoard, clientSocket);
    }

    @Override
    public boolean[][] initializeGame(UUID uuid) {
        voidRunnerBoard.setInitialPosition(5, 5);
        updateHandler.start();
        return new boolean[32][32];

        // TODO send an initial packet to the server and receive the initial
        // player position and board dimensions
    }

    @Override
    public void sendUpdate(UUID uuid, int x, int y) {
        // TODO remove the following line and actually send an UDP packet
        // containing the update to the server
        voidRunnerBoard.handleUpdate(uuid, !(x < 0 || y < 0 || x >= 32 || y >= 32), x, y);
    }

}
