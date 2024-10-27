package ProviderConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that processes input commands and collects and returns output notifications. 
 */
public class ProviderConsumer {
	List<Provider> providerList;
	List<Consumer> consumerList;
	Broker broker;
	ConsumerObserver consumerObserver;

	/**
	 * Constructor which will be called by the test cases to create the instance of this class.
	 */
	public ProviderConsumer() {
		providerList = new ArrayList<Provider>();
		consumerList = new ArrayList<Consumer>();
		broker = new Broker();
		consumerObserver = new ConsumerObserver();
	}

	/**
	 * This method is responsible for removing all the stored published and subscribed events when the getAggregatedOutput( ) method is called.
	 */
	public void reset() {
		providerList.clear();
		consumerList.clear();
		broker = new Broker();
		consumerObserver = new ConsumerObserver();
	}

	/**
	 * This method parses the input. Based on the input command, it will call the appropriate publisher or subscriber.
	 */
	public boolean processInput(String command) {
		String[] list = command.split(",\\s*");
		if(list[0].equals("publish"))
		{
			Provider p = getProvider(list[1]);
			p.publish(list[2], list[3], list[4]);
			return true;
		}
		else if(list[0].equals("subscribe"))
		{
			Consumer c = getConsumer(list[1]);
			c.subscribe(list[2]);
			return true;
		}
		else if(list[0].equals("unsubscribe"))
		{
			Consumer c = getConsumer(list[1]);
			c.unsubscribe(list[2]);
			return true;
		}
		return false;
	}

	/**
	 * This method is responsible for returning the consolidated notifications when the getAggregatedOutput( ) is called.
	 */
	public List<String> getAggregatedOutput() {
		return consumerObserver.getOutput();
	}

	/**
	 * This method check for if the provider is already exists
	 * If not exist, then create new Provider and add to providerList
	 */
	private Provider getProvider(String providerName)
	{
		Provider newProvider = null;
		for (Provider p : providerList)
		{
			if(providerName.equals(p.getProviderName())) {
				newProvider = p;
				break;
			}
		}
		if (newProvider == null)
		{
			newProvider = new Provider(providerName, broker);
			providerList.add(newProvider);
		}
		return newProvider;
	}

	/**
	 * This method check for if the consumer is already exists
	 * If not exist, then create new Consumer and add to consumerList
	 */
	private Consumer getConsumer(String consumerName)
	{
		Consumer newConsumer = null;
		for (Consumer p : consumerList)
		{
			if(consumerName.equals(p.getConsumerName())) {
				newConsumer = p;
				break;
			}
		}
		if(newConsumer == null)
		{
			newConsumer = new Consumer(consumerName, broker);
			newConsumer.addObserver(consumerObserver);
			consumerList.add(newConsumer);
		}
		return newConsumer;
	}
}
