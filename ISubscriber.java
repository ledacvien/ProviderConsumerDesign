package ProviderConsumer;

public interface ISubscriber {

	public abstract boolean subscribe(String exerciseFacility);

	public abstract boolean unsubscribe(String exerciseFacility);

	public abstract void onEvent(Event event);

}
