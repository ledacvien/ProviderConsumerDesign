package ProviderConsumer;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Event {

	public String exerciseFacility;

	public String gymLocation;

	public String gymTiming;

	public String provider;

	private final String[] validLocation = {"mesa", "tempe", "phoenix"};
	private final String[] validFacility = {"cardio", "swimming pool", "yoga"};

	public Event(String provider, String facility, String location, String time) {
		this.provider = provider.toLowerCase();
		this.exerciseFacility = facility.toLowerCase();
		this.gymLocation = location.toLowerCase();
		this.gymTiming = time.toLowerCase();
	}

	public String toString() {
		return exerciseFacility + ": " + provider + ", " + gymLocation + ", " + gymTiming;
	}

	public boolean isSameEvent(Event event) {
		return (provider.equals(event.provider)
				&& exerciseFacility.equals(event.exerciseFacility)
				&& gymLocation.equals(event.gymLocation)
				&& gymTiming.equals(event.gymTiming));
	}

	public boolean isValidEvent() { return (isValidGymLocation() && isValidExerciseFacility() && isValidGymTime()); }

	private boolean isValidGymLocation()
	{
		for(String s : validLocation)
			if(s.equals(gymLocation))
				return true;
		return false;
	}

	private boolean isValidGymTime()
	{
		try{
			String[] time = gymTiming.split(" ");
			if(time.length < 3)
				return false;

			if(time[0].length() < 5)
				time[0] = "0" + time[0];
			if(time[2].length() < 5)
				time[2] = "0" + time[2];
			LocalTime.parse(time[0]);
			LocalTime.parse(time[2]);
			if(time[1].equals("to"))
				return true;

			return false;
		}
		catch (DateTimeParseException e){
			return false;
		}
	}

	private boolean isValidExerciseFacility()
	{
		for(String s : validFacility)
			if(s.equals(exerciseFacility))
				return true;
		return false;
	}
}
