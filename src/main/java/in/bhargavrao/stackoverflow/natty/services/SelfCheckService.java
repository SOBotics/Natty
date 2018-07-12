package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		executorService.scheduleAtFixedRate(()->secureCheck(), 15, 15, TimeUnit.MINUTES);
	}
	
	
	public void check() throws Throwable {
		Instant now = Instant.now();
		Instant lastSuccess = StatusUtils.lastExecutionFinished;
		
		long difference = now.getEpochSecond() - lastSuccess.getEpochSecond();
				
		if (difference > 15*60) {
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
