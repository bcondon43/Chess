package main;

import java.io.IOException;

public interface Network {

	void sendBoardState(int x_i, int y_i, int x, int y) throws IOException;
}
