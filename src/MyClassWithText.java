import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MyClassWithText {
    protected PropertyChangeSupport propertyChangeSupport;
    private String text;

    public MyClassWithText () {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setText(String text) {
        String oldText = this.text;
        this.text = text;
        propertyChangeSupport.firePropertyChange("MyTextProperty",oldText, text);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}

class MyTextListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("MyTextProperty")) {
            System.out.println(event.getNewValue().toString());
        }
    }
}

class MyTextTest {
    public static void main(String[] args) {
        MyClassWithText interestingText = new MyClassWithText();
        MyTextListener listener = new MyTextListener();
        interestingText.addPropertyChangeListener(listener);
        interestingText.setText("FRIST!");
        interestingText.setText("it's more like when you take a car, and you...");
    }
}