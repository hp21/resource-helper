package converter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.google.common.base.Strings;

/**
 *
 */
public class Converter {

    public Converter() {
    }

    public static void main(String[] args) throws IOException {

        new Converter().convert(args);
        System.out.println("Done...");
    }

    private void convert(String[] args) throws IOException {

        final BufferedReader inputReader = new BufferedReader(new InputStreamReader(Converter.class.getResourceAsStream("/" + args[0])));
        final PrintStream printStream = new PrintStream(new FileOutputStream(args[1]));

        String line = null;
        int lineNumber = 1;

        while ((line = inputReader.readLine()) != null) {
            // System.out.println(line);

            final String myLine = Strings.nullToEmpty(line);

            if (isEmpty(myLine)) {
                printStream.println("");
                continue;
            }

            if (isComment(myLine)) {
                printStream.println(line);
                continue;
            }

            final String[] splits = line.split("=");

            if (splits.length != 2) {
                System.out.println("Strange line " + lineNumber + " " + myLine);
                printStream.println(myLine);
                continue;
            }

            printStream.println(createInsert(splits));
            lineNumber++;
        }

    }

    private String createInsert(String[] splits) {
        final String value = String
          .format("INSERT INTO resource_bundle (date_created,date_updated,deleted_flag,language, resource_key, resource, version) VALUES (NOW(),NOW(),0,\"en\",\"%s\",\"%s\",1);", splits[0], splits[1]);
        return value;
    }

    private boolean isEmpty(String myLine) {
        return myLine.isEmpty();
    }

    private boolean isComment(String myLine) {
        return myLine.startsWith("#");
    }
}
