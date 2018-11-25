import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

    public static void main(String[] args){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
    }
}
