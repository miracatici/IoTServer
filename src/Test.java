import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class Test {

	public static void main(String[] args) throws IOException {

		// get URL content
		String url = "https://goo.gl/maps/EEqUtc7Pjfm";
		String ful = expandUrl(url);
		System.out.println(ful);
	}
	public static String expandUrl(String shortenedUrl) throws IOException {
        URL url = new URL(shortenedUrl);    
        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); 
        
        // stop following browser redirect
        httpURLConnection.setInstanceFollowRedirects(false);
         
        // extract location header containing the actual destination URL
        String expandedURL = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();
        System.out.println(expandedURL);
        expandedURL = expandedURL.split("=")[1];
        expandedURL = expandedURL.substring(0, expandedURL.length()-3);
        return expandedURL;
    }
}
