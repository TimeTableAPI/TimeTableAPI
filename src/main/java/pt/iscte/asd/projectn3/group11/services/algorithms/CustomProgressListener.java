package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moeaframework.util.progress.ProgressEvent;
import org.moeaframework.util.progress.ProgressListener;

public final class CustomProgressListener implements ProgressListener {
	private static final Logger LOGGER  = LogManager.getLogger(CustomProgressListener.class);

	private final CustomAlgorithmService algorithmService;

	public CustomProgressListener(CustomAlgorithmService algorithmService) {
		this.algorithmService = algorithmService;
	}

	@Override
	public void progressUpdate(ProgressEvent progressEvent) {
		final double progress = progressEvent.getPercentComplete();
		final double timeLeft = progressEvent.getRemainingTime();
		final String resultForLogging = progress + "%;" + timeLeft + " sec left";
		LOGGER.info(resultForLogging);
		this.algorithmService.setProgress(progress);
	}
}
