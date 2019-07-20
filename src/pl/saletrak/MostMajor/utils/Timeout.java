package pl.saletrak.MostMajor.utils;

public class Timeout {

	public static void setTimeout(Runnable runnable, long delay){
		new Thread(() -> {
			try {
				Thread.sleep(delay);
				runnable.run();
			}
			catch (Exception e){
				System.err.println(e);
			}
		}).start();
	}

}
