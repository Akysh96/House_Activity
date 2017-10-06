package activity;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Operations {
	private Integer i = 1;
	private List<MonitoredData> list = new ArrayList<MonitoredData>();
	private long distinctDays;
	private CheckFilter check;
	private FileWriter fw;

	@SuppressWarnings("static-access")
	public void getDataFromFile() throws IOException, ClassNotFoundException {
		list = Files.lines(Paths.get("Activities.txt"))
				.map(s -> new MonitoredData(s.split("\\s+")[0] + " " + s.split("\\s+")[1],
						s.split("\\s+")[2] + " " + s.split("\\s+")[3], s.split("\\s+")[4]))
				.collect(Collectors.toList());
		distinctDays = list.stream().filter(check.distinctByKey(p -> p.getStartTime().substring(0, 11))).count();
		System.out.println("Distinct Days: " + distinctDays);
		distinctActivities();
		activitiesPerDays();
		totalDuration();
		lessThan5Count();
	}

	public void distinctActivities() {
		openFile("distinctActivities.txt");
		Map<String, Integer> map = list.stream()
				.collect(Collectors.toMap(p -> p.getActivity(), p -> i, (i, i1) -> i + i1));
		map.entrySet().stream()
				.forEach(s -> writeToFile(fw, s.getKey() + " Number of occurence: " + String.valueOf(s.getValue())));
		closeFile();
	}

	public void activitiesPerDays() {
		openFile("activitiesPerDay.txt");
		Map<Integer, Map<String, Integer>> map = list.stream()
				.collect(Collectors.groupingBy(p -> Integer.valueOf(p.getStartTime().substring(8, 11).trim()),
						Collectors.toMap(p -> p.getActivity(), p -> i, (i, i1) -> i + i1)));
		map.entrySet().stream()
				.forEach(s -> writeToFile(fw, s.getKey() + " " + s.getValue().entrySet().toString() + "\n"));
		closeFile();
	}

	public void totalDuration() {
		openFile("totalDurationMoreThan10H.txt");
		Map<String, Integer> map = list.stream().map(p -> {
			p.setDuration(getTimePassed(p.getStartTime(), p.getEndTime()));
			return p;
		}).collect(Collectors.toMap(p -> p.getActivity(), p -> p.getDuration(),
				(duration1, duration2) -> duration1 + duration2));
		map.entrySet().stream().filter(s -> s.getValue() / 60 > 10).forEach(
				s -> writeToFile(fw, s.getKey() + " " + s.getValue() / 60 + ":" + s.getValue() % 60 + " HH:mm"));
		closeFile();
	}

	public Integer getTimePassed(String startTime, String endTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime from = LocalDateTime.parse(startTime, formatter);
		LocalDateTime until = LocalDateTime.parse(endTime, formatter);
		long time = from.until(until, ChronoUnit.MINUTES);
		return (int) time;
	}

	@SuppressWarnings("static-access")
	public void lessThan5Count() {
		openFile("ActivitiesLessThan5Min.txt");
		List<String> list1 = list.stream().filter(check.distinctByKey(p -> p.getActivity())).filter(p -> {
			return isTrue(list, p.getActivity());
		}).map(p -> p.getActivity()).collect(Collectors.toList());
		list1.stream().forEach(p -> writeToFile(fw, p.toString()));
		closeFile();
	}

	public boolean isTrue(List<MonitoredData> list, String nume) {
		Double count = (double) list.stream().filter(p -> p.getActivity().equals(nume)).count();
		Double less5 = (double) list.stream().filter(p -> p.getActivity().equals(nume)).map(p -> p.getDuration())
				.filter(p -> p < 5).count();
		Double per = (less5 / count) * 100;
		System.out.println(per);
		if (per >= 90.0) {
			return true;
		}
		return false;
	}

	private void writeToFile(FileWriter fw, String p) {
		try {
			fw.write(String.format("%s%n", p));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void openFile(String name) {
		try {
			fw = new FileWriter(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeFile() {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<MonitoredData> getList() {
		return list;
	}
}
