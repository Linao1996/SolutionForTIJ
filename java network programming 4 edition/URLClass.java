package pac2;

import kotlin.jvm.Synchronized;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Created by lucas on 4/16/17.
 */
public class URLClass {
    static void testProtocol(String url) {
        try {
            URL u = new URL(url);
            System.out.println(u.getProtocol() + " is supported");
        } catch (MalformedURLException e) {
            String protocol = url.substring(0, url.indexOf(":"));
            System.out.println(protocol + " is not supported");
//            e.printStackTrace();
        }
    }

    static void openUrl() {
        try {
            URL u = new URL("http://www.audubon.org/123.html");
            URL u2 = new URL(u, "456.html");
            URL u3 = new URL("http", "www.audubon.org", "/123.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getURLInfo() {
        try {
//            URL url = new URL("https://www.zhihu.com");
            URL url = new URL("https://www.google.com.hk");
            InputStream in = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String s;
            while ((s = bufferedReader.readLine()) != null) {// however, you can't presume url must point to a text file;
                System.out.println(s);
            }
//            int c;
//            while((c = in.read())!=-1){// automatically new line when read \n;
//                system.out.print(65) : 65
//                System.out.write(65) : A
//                System.out.write(c);//or you can write as System.out.print((char)c);
//            }
            in.close();
//            Scanner s = new Scanner(in);
//            while(s.hasNextLine()){
//                System.out.println(s.nextLine());
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getContent() {
        try {
            URL u = new URL("https://www.zhihu.com");
            Object o = u.getContent();
            System.out.println("I got a " + o.getClass().getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encodeDecode() {
        try {
            System.out.println(URLEncoder.encode("This*String#has+spaces/and?lots of%other\\things", "UTF-8"));
            //Note that this is not the proper way to encode, since URLEncoder.encode would encode String indiscriminately.
            System.out.println(URLEncoder.encode("https://www.google.com/search?hl=en&as_q=Java&as_epq=I/O", "utf-8"));
            //instead, you should do this:
            String url = "http://www.google.com/search?hl=en&as";
            url += URLEncoder.encode("_", "utf-8");
            url += "q=Java&as";
            url += URLEncoder.encode("_", "utf-8");
            url += "epq=I";
            url += URLEncoder.encode("/", "utf-8");
            url += "O";
            System.out.println(url);// result: http://www.google.com/search?hl=en&as_q=Java&as_epq=I%2FO;
            //In contrast, you can just decode the url;
            String output = URLDecoder.decode(url, "utf-8");
            System.out.println(output);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void searchWeb() {
        String target = "";
        target += "zhihu";
        target = target.trim();
        QueryString query = new QueryString();
        query.add("q", target, true);
        try {
            URL url = new URL("https://www.github.com/search?" + query.getQuery());
            System.out.println(url.toString());
            InputStream in = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        testProtocol("http://www.123.com");
        testProtocol("https://www.123.com");
        testProtocol("ftp://www.123.com");
        System.out.println("===================================================");
//        openUrl();
        System.out.println("===================================================");
//        getURLInfo();
        System.out.println("===================================================");
//        getContent();
        System.out.println("===================================================");
//        encodeDecode();
        System.out.println("===================================================");
        searchWeb();

    }
}

class QueryString {
    StringBuilder query = new StringBuilder();

    public QueryString() {
    }

    public synchronized void add(String name, String value) {
        add(name, value, false);
    }

    public synchronized void add(String name, String value, boolean first) {
        if (!first) {
            query.append("&");
        }
        encode(name, value);
    }

    public synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "utf-8"));
            query.append("=");
            query.append(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getQuery() {
        return query.toString();
    }

    public String toString() {
        return query.toString();
    }

}
