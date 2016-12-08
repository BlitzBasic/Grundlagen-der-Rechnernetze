package de.uulm.in.vs.grn.d1;

import java.net.DatagramSocket;

public class UpdateHandler extends Thread {

    private VoidRunnerBoard voidRunnerBoard;
    private DatagramSocket clientSocket;

    public UpdateHandler(VoidRunnerBoard voidRunnerBoard, DatagramSocket clientSocket) {
        this.voidRunnerBoard = voidRunnerBoard;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        // TODO receive and parse updates send by the server
        // voidRunnerBoard.handleUpdate(id, alive, x, y);
    }

}
