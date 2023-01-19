import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    List<String> containedStrings = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Welcome to the Search Engine";
        }
        else if (url.getPath().equals("/add")) {
            String[] params = url.getQuery().split("=");
            if (params[0].equals("s")) {
                containedStrings.add(params[1]);
                return String.format("%s has been added", params[1]);
            }
            return String.format("Need proper format to use add: ?s=[your string]");
        }
        else if (url.getPath().equals("/search")) {
            String[] params = url.getQuery().split("=");
            if (params[0].equals("s")) {
                List<String> contained = new ArrayList<>();
                for (String item : containedStrings) {
                    if (item.contains(params[1])) {
                        contained.add(item);
                    }
                }
                return contained.toString();
            }
            return String.format("Need proper format to use search: ?s=[your string]");
        }
        else {
            return "404 not found";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}