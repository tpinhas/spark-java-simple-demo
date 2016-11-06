package phonebook;

import html.HTMLTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class PhoneBookWebApp {

    private static final Map<String, Object> phoneBook = new ConcurrentHashMap<>();

    private final static String header = "She codes members phone book";
    static HTMLTemplate htmlTemplate = new HTMLTemplate(header);

    public static void main(String[] args) {
        JsonPhoneRepository.load(phoneBook);

        get("/about", (request, response) ->
                htmlTemplate.answer("Exclusive she coder phone book to stay in touch with your friends"));

        get("/list", (request, response) ->
                htmlTemplate.answer(htmlTemplate.mapToHtmlTable(phoneBook, "member name", "phone no.")));

        // matches "GET /find/Tohar"
        get("/find/:name", (request, response) -> {
            String memberName = request.params(":name");
            if (phoneBook.containsKey(memberName)) {
                return htmlTemplate.answer("Phone for member " + request.params(":name") + " is " + phoneBook.get(memberName));
            } else {
                return htmlTemplate.answer("Sorry. Member " + request.params(":name") + " is not found in she-codes phone book");
            }
        });

        // matches "POST /add/Reut/with/5678"
        post("/add/*/with/*", (request, response) -> {
            phoneBook.put(request.splat()[0], request.splat()[1]);
            JsonPhoneRepository.save(phoneBook);
            return htmlTemplate.answer("Member added!");
        });
    }

}
