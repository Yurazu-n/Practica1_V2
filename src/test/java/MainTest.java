import org.example.control.MyExecutionException;
import org.example.control.WeatherControl;
import org.example.view.WeatherInterface;

public class MainTest {
    public static void main(String[] args) throws MyExecutionException {
        WeatherInterface weatherInterface = new WeatherInterface();
        WeatherControl weatherControl = new WeatherControl();
        weatherControl.execute(args[0], args[1]);

        weatherInterface.run(args[0]);
    }
}