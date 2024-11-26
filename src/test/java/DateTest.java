
import cc.altius.utils.DateUtils;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author akil
 */
public class DateTest {

    public static void main(String[] args) {
        Date stopDate = DateUtils.getEndOfMonthObject();
        Date startDate = DateUtils.getStartOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.PST), -18));
        System.out.println("startDate = " + startDate);
        System.out.println("stopDate = " + stopDate);
    }

}
