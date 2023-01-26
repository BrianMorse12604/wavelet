import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The String message that is manipulated and saved on the server
    String message = "";

    public String handleRequest(URI url) {
        //Check the path of the url
        if (url.getPath().equals("/add-message")) {
            //Check if there is a valid query
            String[] query = url.getQuery().split("=");
            if (query.length == 2 && query[0].equals("s")) {
                //Add the new message on and return it
                if (message.equals("")) {
                    message += query[1];
                }
                else {
                    message += "\n" + query[1];
                }
                return message;
            }
        }
        return "404 Not Found!";
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}