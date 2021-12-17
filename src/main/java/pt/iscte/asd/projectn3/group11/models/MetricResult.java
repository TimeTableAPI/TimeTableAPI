package pt.iscte.asd.projectn3.group11.models;

public class MetricResult {

	public final String metricName;
	public final Float result;

	public MetricResult(String metricName, Float result) {
		this.metricName = metricName;
		this.result = result;
	}

	@Override
	public String toString() {
		return "MetricResult{" +
				"metricName='" + metricName + '\'' +
				", result=" + result +
				'}';
	}
}
