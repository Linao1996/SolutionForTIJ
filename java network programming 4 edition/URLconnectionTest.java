package pac2;


import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by lucas on 4/16/17.
 */
class NoGovernmentCookies implements CookiePolicy {

    @Override
    public boolean shouldAccept(URI uri, HttpCookie httpCookie) {
        if (uri.getAuthority().toLowerCase().equals("gov") || httpCookie.getDomain().toLowerCase().equals("gov")) {
            return false;
        }
        return true;
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

class FormPost{
    private URL url;
    private QueryString query = new QueryString();
    public FormPost(URL url){
        if(!url.getProtocol().toLowerCase().equals("http") && !url.getProtocol().toLowerCase().equals("https")){
            throw new IllegalArgumentException("Posting work only for http URLS");
        }
        this.url = url;
    }
    public void add(String name, String value){
        query.add(name,value);
    }
    public URL getUrl(){
        return url;
    }
    public InputStream post() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        List<String> cookie;
        try(PrintWriter out = new PrintWriter(connection.getOutputStream())){
            System.out.println(query);
            out.println(query);// query:  name=lucas&email=12345%40gmail.com
            out.flush();
        }
        return connection.getInputStream();
    }

    public static void main(String[] args){

        try {
            URL url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            FormPost fp = new FormPost(url);
            fp.query.add("name","lucas");
            fp.query.add("email","12345@gmail.com");
            InputStream in = fp.post();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = bufferedReader.readLine())!=null){
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
public class URLConnection2 {
    public static void getInfo(URLConnection connection) throws IOException {
        InputStream in = null;//until now, when getInputStream is called;
        try {
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void getHeader(URLConnection connection) {
        int j = 0;
        while (true) {
            String header = connection.getHeaderField(j);
            if (header == null) {
                break;
            }
            System.out.println(connection.getHeaderFieldKey(j++) + ":" + header);
        }

    }

    public static void main(String[] args) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(new NoGovernmentCookies());
        CookieHandler.setDefault(cookieManager);
        CookieStore store = cookieManager.getCookieStore();
        try {
            URL url = new URL("https://www.zhihu.com");
            URLConnection connection = url.openConnection();//not connected
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(45000);
            connection.setDoOutput(true);// URLConnection, by default, is not allowed to output, so you have to set it as true;
//            getInfo(connection);
            System.out.println("============================================");
            System.out.println(connection.getContentType());
            System.out.println(connection.getContentLength());
            System.out.println("============================================");
//            getHeader(connection);
            System.out.println("============================================");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
