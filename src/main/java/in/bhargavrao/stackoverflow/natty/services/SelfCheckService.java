package in.bhargavrao.stackoverflow.natty.services;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;

/**
 * Checks the status of the bot and maybe triggers a reboot
 * */
public class SelfCheckService {
	
	private ScheduledExecutorService executorService;
	private RunnerService instance;
	
	public SelfCheckService(RunnerService runner) {
		this.instance = runner;
		this.executorService = Executors.newSingleThreadScheduledExecutor();
	}
	
	public void start() {
		executorService.scheduleAtFixedRate(()->secureCheck(), 1, 5, TimeUnit.MINUTES);
	}
	
	
	public void check() throws Throwable {
		Instant now = Instant.now();
		Instant lastSuccess = StatusUtils.lastExecutionFinished;
		
		long difference = now.getEpochSecond() - lastSuccess.getEpochSecond();
				
		if (difference > 5*60) {
			this.instance.reboot();
		}
	}
	
	public void secureCheck() {
		try {
			this.check();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
