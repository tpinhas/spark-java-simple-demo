package html;

import java.util.Map;

public class HTMLTemplate {
    public final String PAGE_HEADER;
    public static final String PAGE_FOOTER = "</body></html>";

    public HTMLTemplate(String header){
        PAGE_HEADER = "<!DOCTYPE html>\n"+
                "<html>\n"+
                "<body>\n"+
                "<h1>" + header + "</h1>\n";
    }

    public String answer(String response) {
        return PAGE_HEADER + response + PAGE_FOOTER;
    }

    public String mapToHtmlTable(Map<String, Object> phoneBook, String tableCol1, String tableCol2) {
        String table = "<table>";
        table += "<tr><td><b>" + tableCol1 + "</b></td><td><b>" + tableCol2 + "</b></td></tr>";
        for(Map.Entry entry: phoneBook.entrySet()) {
            table += "<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>";
        }
        table += "</table>";
        return table;
    }

    public String home() {
        return "<div><a href=\"/\">home</a></div>";
    }
}
