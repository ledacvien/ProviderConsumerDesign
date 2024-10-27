package ProviderConsumer;

public interface IPublisher {

	public abstract boolean publish(String exerciseFacility, String gymLocation, String gymTiming);

}
