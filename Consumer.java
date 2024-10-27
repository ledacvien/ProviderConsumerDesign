package ProviderConsumer;

public class Consumer implements ISubscriber {

	private final String consumerName;
	private final Broker broker;
	private ConsumerObserver subscribedEvents;

	public Consumer(String consumerName, Broker broker) {
		this.consumerName = consumerName;
		this.broker = broker;
	}

	public void addObserver(ConsumerObserver observer) {
		this.subscribedEvents = observer;
	}

	@Override
	public void onEvent(Event event) {
		String ret = consumerName + " notified " + event.toString();
		//System.out.println(ret);
		subscribedEvents.update(ret);
	}


	public String getConsumerName() {
		return consumerName;
	}


	@Override
	public boolean subscribe(String exerciseFacility) {
		return broker.attachConsumer(this, exerciseFacility);
	}


	@Override
	public boolean unsubscribe(String exerciseFacility) {
		return broker.detachConsumer(this, exerciseFacility);
	}

}
