package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class StreamUtil {

    public static String parserStreamUtil(InputStream in) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        StringWriter sw = new StringWriter();
        String str = null;
        while ( (str = br.readLine()) != null){
              sw.write(str);
        }
        sw.close();
        br.close();
        return sw.toString();
    }

}
