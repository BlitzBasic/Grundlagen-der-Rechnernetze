package de.uulm.in.vs.grn.d1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class UpdateHandler extends Thread {

    private VoidRunnerBoard voidRunnerBoard;
    private DatagramSocket clientSocket;

    public UpdateHandler(VoidRunnerBoard voidRunnerBoard, DatagramSocket clientSocket) {
        this.voidRunnerBoard = voidRunnerBoard;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        
    	while(true){
    	byte[] receiveData = new byte[244];

		try {
			
			DatagramPacket incomingPacket = new DatagramPacket(receiveData, receiveData.length);

			clientSocket.receive(incomingPacket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ByteBuffer buffer = ByteBuffer.wrap(receiveData);
    	
		int nrOfUpdates = buffer.getInt();
		System.out.println(nrOfUpdates);
		for(int i=0; i<nrOfUpdates; i++){
			UUID uuid = new UUID(buffer.getLong(), buffer.getLong());
			System.out.println(uuid.toString());
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			System.out.println(buffer.position());
			int x=buffer.getInt();
			System.out.println(buffer.position());
			int y=buffer.getInt();
			boolean alive = !(x < 0 || y < 0 || x >= voidRunnerBoard.boardHeight || y>= voidRunnerBoard.boardWidth);
			System.out.println("posX: " + x + " posY: " + y + " alive: " + alive);
			voidRunnerBoard.handleUpdate(uuid, alive, x, y);
		}
    	}
    	
    	// TODO receive and parse updates send by the server
        // voidRunnerBoard.handleUpdate(id, alive, x, y);
    }

}
