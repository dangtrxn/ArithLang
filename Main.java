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
            //initialize memory
            //file object of input file and scanner to read file
            Memory memory = new Memory();
            File source = new File(args[0]);
            br = new BufferedReader(new FileReader(source));
            int lineNum = 0;
            String line;

            System.out.println("Program Start\n---------------");
            //loop through file lines
            while((line = br.readLine()) != null){
                //remove whitespace and skip empty lines
                line = line.trim();
                if(line.isEmpty()) continue;

                //create lex + parser
                //parse through expressions and create parse trees
                LexicalAnalyzer lex = new LexicalAnalyzer(line);
                Parser parser = new Parser(lex);
                ParseTree parseTree = new ParseTree(parser.parse());

                System.out.println("Executing line: " + line);
                parseTree.evaluate(memory);

                lineNum++;
            }

            br.close();

        //catch exceptions
        }
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