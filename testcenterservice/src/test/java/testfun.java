import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public class testfun {
    public static void main(final String[] args) {

        final String json1 = "{\n" +
                "\t\"code111\":200,\n" +
                "\t\"data\":{\n" +
                "\t\t\"endRow\":1,\n" +
                "\t\t\"hasNextPage\":false,\n" +
                "\t\t\"list\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
                "\t\t\t\t\"mem\":\"8G\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
                "\t\t\t\t\"mem\":\"8G\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"navigateFirstPage\":1,\n" +
                "\t\t\"total\":1\n" +
                "\t}\n" +
                "}";
        final String json2 = "{\n" +
                "\t\"code\":200,\n" +
                "\t\"data\":{\n" +
                "\t\t\"endRow\":1,\n" +
                "\t\t\"hasNextPage\":false,\n" +
                "\t\t\"list\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
                "\t\t\t\t\"mem\":\"8G\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
                "\t\t\t\t\"mem\":\"8G\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"navigateFirstPage\":1,\n" +
                "\t\t\"total\":1\n" +
                "\t}\n" +
                "}";
        CustomComparator customComparator = new CustomComparator(JSONCompareMode.NON_EXTENSIBLE);
        try {
            JSONCompareResult result = JSONCompare.compareJSON(json1, json2, customComparator);

            result.getFieldFailures().forEach(fieldComparisonFailure -> {
                System.out.println(String.format("变更字段：%s，变更前值：%s，变更后值：%s",fieldComparisonFailure.getField(),fieldComparisonFailure.getExpected(),fieldComparisonFailure.getActual()));
            });
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
