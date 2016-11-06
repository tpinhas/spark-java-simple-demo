package poll;

import html.HTMLTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class PollWebApp {
    private final static Map<String, Object> pollResults = new ConcurrentHashMap<>();

    private final static String header = "She codes coding skills poll";
    static HTMLTemplate htmlTemplate = new HTMLTemplate(header);

    public static void main(String[] args) {
        PollWebApp poll = new PollWebApp();
        poll.launchPoll();
    }

    public void launchPoll() {

        // serving a static page
        get("/", (req, res) -> renderContent("../index.html"));

        get("/listPoll", (request, response) -> htmlTemplate.answer(htmlTemplate.mapToHtmlTable(pollResults, "name", "answer") + htmlTemplate.home()));

        get("/resultPoll", (request, response) -> htmlTemplate.answer(htmlTemplate.mapToHtmlTable(resultSummary(), "answer", "no. of votes") + htmlTemplate.home()));

        post("/submitPoll", (request, response) -> {
            pollResults.put(request.queryParams("name"), request.queryParams("pollAnswer"));
            return htmlTemplate.answer("Thanks for your answer" + htmlTemplate.home());
        });
    }

    private static Map<String, Object> resultSummary() {
        Map<String, Object> pollSummary = new ConcurrentHashMap<>();
        pollSummary.put("Read a book or manual", 0);
        pollSummary.put("Take a course", 0);
        pollSummary.put("Find information on the internet", 0);
        pollSummary.put("Ask a friend to teach you", 0);
        pollSummary.put("Simply try to use it", 0);
        for (Object answer : pollResults.values()) {
            pollSummary.put(answer.toString(), (Integer) pollSummary.get(answer) + 1);
        }
        return pollSummary;
    }

    // render html content
    private String renderContent(String htmlFile) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getResource(htmlFile).toURI())), StandardCharsets.UTF_8);
    }
}
