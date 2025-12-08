import java.io.*;
import domain.*;
import globalexceptions.*;

/**
 * program reads from input file
 * through lexical analyzer object, creates tokens and displays token information
 * catches any exceptions
 */
public class Main {
    public static void main(String[] args){
        //do not run program without a given input file
        if (args.length == 0){
            System.err.println("Usage: java Main <inputfile>");
            return;
        }

        BufferedReader br = null;
        try{
            //file object of input file and buffered reader to read file
            File source = new File(args[0]);
            br = new BufferedReader(new FileReader(source));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');  // keep the newlines
            }
            br.close();

            //lexical analyzer, parser, and parse tree creation
            LexicalAnalyzer lex = new LexicalAnalyzer(sb.toString());
            Parser parser = new Parser(lex);
            ParseTree parseTree = new ParseTree(parser.parse());

            //program execution
            System.out.println("Program Start\n---------------");
            parseTree.evaluate();
        }
        //catch exceptions
        catch (InvalidTokenException e){
            System.err.println(e.getMessage());
        }
        catch(InvalidArgumentException e){
            System.err.println(e.getMessage());
        }
        catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
        catch(InvalidParseException e){
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Unexpected error... terminating");
        }
    }
}