package ProviderConsumer;

public class Provider implements IPublisher {

	private final String providerName;

	private final Broker broker;

	public Provider(String providerName, Broker broker) {
		this.providerName = providerName;
		this.broker = broker;
	}

	public String getProviderName() {
		return providerName;
	}


	@Override
	public boolean publish(String exerciseFacility, String gymLocation, String gymTiming) {
		Event event = new Event(providerName, exerciseFacility, gymLocation, gymTiming);
		return broker.pushEvent(event);
	}

}
