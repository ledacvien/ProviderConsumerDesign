package ProviderConsumer;
import java.beans.Customizer;
import java.util.ArrayList;
import java.util.List;

public class ConsumerObserver{
    private List<String> output;

    public ConsumerObserver(){
        output = new ArrayList<>();
    }

    public void update(String output)
    {
        this.output.add(output);
    }

    public List<String> getOutput() {
        return output;
    }
}
