package activity;

public class MonitoredData {

	private String startTime;
	private String endTime;
	private String activity;
	private Integer duration;

	public MonitoredData(String startTime, String endTime, String activity) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activity = activity;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getActivity() {
		return activity;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getDuration() {
		return duration;
	}

}
