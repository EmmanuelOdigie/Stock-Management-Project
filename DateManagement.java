package stockManagement;

public class DateManagement {

    public static String getDatabaseDate(int year, int mon, int day) {
        year += 1900;
        mon += 1;
        String m = ""+mon;
        if(m.length() == 1)
            m = "0"+m;
        String d = ""+day;
        if(d.length() == 1)
            d = "0"+d;
        return ""+year+"-"+m+"-"+d;
    }


}
