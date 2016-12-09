package de.uulm.in.vs.grn.d1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class ServerConnectionImpl implements VoidRunnerBoard.ServerConnection {

	private VoidRunnerBoard voidRunnerBoard;
	private InetSocketAddress serverEndpoint;
	private DatagramSocket clientSocket;
	private UpdateHandler updateHandler;

	public ServerConnectionImpl(VoidRunnerBoard voidRunnerBoard, InetSocketAddress serverEndpoint) throws IOException {
		this.voidRunnerBoard = voidRunnerBoard;
		this.serverEndpoint = serverEndpoint;

		clientSocket = new DatagramSocket(); // TODO: DAFUQ? enter port?
												// broadcast everywhere? WOW!
												// Much intelligent.

		updateHandler = new UpdateHandler(voidRunnerBoard, clientSocket);
	}

	@Override
	public boolean[][] initializeGame(UUID uuid) {

		long leastSig = uuid.getLeastSignificantBits();
		long mostSig = uuid.getMostSignificantBits();

		// Solution is in progress.. Don't touch this shit
		// create bytearray --> maybe --> don't know what I do --> shit -->
		// Peter (or maybe I) will fix it #noCommentAboutWhatThisArrayMeans
		// #haveFunPeter
		// #veryNetworkMuchWowSuchUDP
		byte[] payload = new byte[] { (byte) (mostSig >>> 56), (byte) (mostSig >>> 48), (byte) (mostSig >>> 40),
				(byte) (leastSig >>> 32), (byte) (mostSig >>> 24), (byte) (mostSig >>> 16), (byte) (mostSig >>> 8),
				(byte) mostSig, (byte) (leastSig >>> 56), (byte) (leastSig >>> 48), (byte) (leastSig >>> 40),
				(byte) (leastSig >>> 32), (byte) (leastSig >>> 24), (byte) (leastSig >>> 16), (byte) (leastSig >>> 8),
				(byte) leastSig, -1, -1, -1, -1, -1, -1, -1, -1 };

		System.out.println(Arrays.toString(payload));

		DatagramPacket outPacket = new DatagramPacket(payload, payload.length, serverEndpoint); // TODO:
																								// Port??

		byte[] receiveData = new byte[24];

		try {
			clientSocket.send(outPacket);
			DatagramPacket incomingPacket = new DatagramPacket(receiveData, receiveData.length);

			clientSocket.receive(incomingPacket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int posx = 0;
		int posy = 0;
		int boardWidth = 0;
		int boardHeight = 0;

		// for (int i = 0; i < 4; i++) {
		// int buffer = in.read();
		// System.out.println(buffer);
		// newLength += buffer << 8 * (3 - i);
		// }

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
