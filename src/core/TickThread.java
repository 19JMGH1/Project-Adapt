package core;

//This class is used to speed up the game. It allows the tick method to run in a separate thread. Meaning that rendering, and ticking happen simultaneously.
public class TickThread implements Runnable{

	public Thread thread2;
	public boolean running = false;

	private Main_Game game;

	public TickThread(Main_Game game)
	{
		this.game = game;
	}

	public synchronized void start() {
		//System.out.println(HEIGHT+", "+WIDTH);
		running = true;
		thread2 = new Thread(this);
		thread2.start();
		System.out.println("Second thread started");
	}

	public void run() {
		running = true;
		long now;
		long updateTime;
		long wait;
		final long OPTIMAL_TIME = 1000000000 / Main_Game.Target_TPS;
		while (running) {
			now = System.nanoTime();
			if (game.Paused == false) {
				game.tick();
			}
			updateTime = System.nanoTime() - now;
			wait = (OPTIMAL_TIME - updateTime) / 1000000;
			if (wait > 0)
			{
				try {
					Thread.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//System.out.println("FPS: "+(1000/wait));
			//System.out.println("Tick: "+wait);
		}
	}
}
