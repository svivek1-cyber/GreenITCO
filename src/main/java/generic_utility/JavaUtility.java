package generic_utility;
import java.time.LocalDate; 
import java.time.LocalTime;
import static java.time.LocalTime.now;


public class JavaUtility {
    public static int generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 9999);
        return randomNumber;
    }
    
    public static String getCurrentDateAndTime() {
        String currentDateAndTime = ""; // tostring

        LocalDate current = LocalDate.now();
        int currentYear = current.getYear();
        int currentMonth = current.getMonthValue();
        int currentDay = current.getDayOfMonth();

        LocalTime currentTime = now();
        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();
        int currentSecond = currentTime.getSecond();
        currentDateAndTime = currentDateAndTime + currentDay + "/"+ currentMonth + "/"+ currentYear + "_" + currentHour + ":"+ currentMinute + ":"+ currentSecond;
        return currentDateAndTime;
    }
    
    // public static void main(String[] args) {
    //     System.out.println(generateRandomNumber());
    //     System.out.println(getCurrentDateAndTime());
    //     System.out.println(currentTime());
    // }

    public static String currentTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.toString();
    }

}
