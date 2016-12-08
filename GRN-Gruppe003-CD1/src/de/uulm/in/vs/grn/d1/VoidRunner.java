package de.uulm.in.vs.grn.d1;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import javax.swing.JFrame;

public class VoidRunner extends JFrame {

    private static final long serialVersionUID = 1L;

    public VoidRunner() throws IOException {
        InetSocketAddress gameServer = new InetSocketAddress(InetAddress.getByName("134.60.77.152"), 6666);
        VoidRunnerBoard board = new VoidRunnerBoard(gameServer);
        add(board);

        setSize(board.boardWidth, board.boardHeight);
        setResizable(false);

        setTitle("GRN VoidRunner");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame ex = new VoidRunner();
                ex.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
