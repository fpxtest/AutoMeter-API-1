public class testfun {
    public static void main(final String[] args) {

        String res= EncryptUtil.getInstance().MD5("123456");
        String res1="";
         res1= EncryptUtil.getInstance().SHA1("123456","121212");
        //String res3= EncryptUtil.getInstance().(res1,"121212");


        System.out.println(res1);
        //System.out.println(res3);


    }
}
