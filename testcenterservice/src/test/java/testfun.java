import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.util.Calendar;
import java.util.Date;

public class testfun {
    public static void main(final String[] args) {


        Calendar cal = Calendar.getInstance();

        String CurrentTime = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + " " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":00";

        //String CurrentTime = String.valueOf(cal.get(Calendar.YEAR)) + "-" + String.valueOf(cal.get(Calendar.MONTH)) + "-" + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":00";
        if(CurrentTime.equals("2021-11-26 7:51:00"))
        {
            System.out.println(100000000);
        }


//        final String json1 = "{\n" +
//                "\t\"code111\":200,\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"endRow\":1,\n" +
//                "\t\t\"hasNextPage\":false,\n" +
//                "\t\t\"list\":[\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t}\n" +
//                "\t\t],\n" +
//                "\t\t\"navigateFirstPage\":1,\n" +
//                "\t\t\"total\":1\n" +
//                "\t}\n" +
//                "}";
//        final String json2 = "{\n" +
//                "\t\"code\":200,\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"endRow\":1,\n" +
//                "\t\t\"hasNextPage\":false,\n" +
//                "\t\t\"list\":[\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t}\n" +
//                "\t\t],\n" +
//                "\t\t\"navigateFirstPage\":1,\n" +
//                "\t\t\"total\":1\n" +
//                "\t}\n" +
//                "}";
//        CustomComparator customComparator = new CustomComparator(JSONCompareMode.NON_EXTENSIBLE);
//        try {
//            JSONCompareResult result = JSONCompare.compareJSON(json1, json2, customComparator);
//
//            result.getFieldFailures().forEach(fieldComparisonFailure -> {
//                System.out.println(String.format("变更字段：%s，变更前值：%s，变更后值：%s",fieldComparisonFailure.getField(),fieldComparisonFailure.getExpected(),fieldComparisonFailure.getActual()));
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//


    }
}
