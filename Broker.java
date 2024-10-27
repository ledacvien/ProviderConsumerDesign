package ProviderConsumer;

import java.util.*;

public class Broker {
	/**
	 * map each exercise facility to a list of consumer
	 */
	private final Map<String, List<ISubscriber>> mFacilityToConsumer;

	/**
	 * Duplicate Event will be ignored.
	 */
	private final Set<Event> events;

	public Broker(){
		mFacilityToConsumer = new HashMap<String, List<ISubscriber>>();
		mFacilityToConsumer.put("cardio", new ArrayList<ISubscriber>());
		mFacilityToConsumer.put("swimming pool", new ArrayList<ISubscriber>());
		mFacilityToConsumer.put("yoga", new ArrayList<ISubscriber>());
		events = new HashSet<Event>();
	}

	/**
	 * attach a consumer to his/her subscribing facility
	 * If it is a new Facility, then create new ArrayList of ISubscriber corresponding to that facility
	 */
	public boolean attachConsumer(ISubscriber consumer, String exerciseFacility) {
		// If Facility is one of: cardio, swimming pool, yoga
		if(mFacilityToConsumer.containsKey(exerciseFacility))
		{
			// If the consumer not subscribe to that activity, then add new subscriber of the Facility
			if(!mFacilityToConsumer.get(exerciseFacility).contains(consumer)) {
				mFacilityToConsumer.get(exerciseFacility).add(consumer);
				return true;
			}
		}
		// Facility is not valid, or already subscribe
		return false;
	}

	/**
	 * pushEvent will add new event to event pool, and notify corresponding consumers.
	 */
	public boolean pushEvent(Event event) {
		// No duplicate event allow
		if(!event.isValidEvent() || isDuplicateEvent(event)) {
			return false;
		}
		// Event is unique
		events.add(event);
		// Notify all subscriber of the event
		List<ISubscriber> subscribers = mFacilityToConsumer.get(event.exerciseFacility);
		for(ISubscriber sub : subscribers)
		{
			sub.onEvent(event);
		}
		return true;
	}

	/**
	 * remove subscription of a consumer to his/her facility
	 */
	public boolean detachConsumer(ISubscriber consumer, String exerciseFacility) {
		if(mFacilityToConsumer.containsKey(exerciseFacility))
		{
			if (mFacilityToConsumer.get(exerciseFacility).contains(consumer)) {
				mFacilityToConsumer.get(exerciseFacility).remove(consumer);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * This method check if an Event is already exist.
	 */
	private boolean isDuplicateEvent(Event event) {
		for(Event e : events){
			if (event.isSameEvent(e))
				return true;
		}
		return false;
	}
}
