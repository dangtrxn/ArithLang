package domain;

import globalexceptions.*;
import domain.node.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class for a parser which contains information, parse method, match method, and necessary recursive methods for recursive descent
 */
public class Parser {
    private LexicalAnalyzer lex;
    private Token current_token;

    /**
     * constructor for parser
     * @param lex - lexical analyzer
     * @throws InvalidArgumentException if lex is null
     */
    public Parser(LexicalAnalyzer lex) throws InvalidArgumentException {
        if(lex == null){
            throw new InvalidArgumentException("Null lexical analyzer in Parser constructor.");
        }
        this.lex = lex;
        this.current_token = lex.get_token();
    }

    /**
     * method to start parsing a program
     * @return program node that is root of parse tree
     */
    //start symbol:
    //<Program> ::= <Stmt_List>
    public ProgramNode parse(){
        ProgramNode programNode = new ProgramNode(new ArrayList<>());

        while(current_token.get_type() != TokenType.EOS){
            if(current_token.get_type() == TokenType.EOL){
                match(TokenType.EOL);
                continue;
            }
            programNode.addStatement(statement());
        }
        return programNode;
    }

    /**
     * method to match current token to expected token type
     * @param expected token type of expected token
     * @throws InvalidParseException if tokens do not match
     */
    public void match(TokenType expected) throws InvalidParseException {
        //check if current token matches expected, if so get next token
        if(current_token.get_type() == expected){
            current_token = lex.get_token();
        }
        else{
            throw new InvalidParseException("Invalid token \"" + current_token + "\" on row " + current_token.get_row() + ", col " + current_token.get_col() + " while parsing. Expected " + expected);
        }
    }

    /**
     * method for a statement definition
     * @throws InvalidParseException if statement is unrecognized
     * @return specific statement node
     */
    //<Statement> ::= <Assn_Stmt> | <Display_Stmt> | <Input_Stmt>
    public StatementNode statement() throws InvalidParseException{
        switch(current_token.get_type()){
            case LET:
                return assign_statement();
            case DISPLAY:
                return display_statement();
            case INPUT:
                return input_statement();
            case IF:
                return if_statement();
            case WHILE:
                return while_statement();
            case FOR:
                return for_statement();
            default:
                throw new InvalidParseException("Invalid statement while parsing: " + current_token);
        }
    }

    /**
     * method for an assignment statement definition
     * @return let statement node
     */
    //<Assn_Stmt> ::= “let” <id> “:=” <Arithmetic_Expression> <EOL>
    public LetStatementNode assign_statement(){
        match(TokenType.LET);
        String id = identifier();
        match(TokenType.ASSIGNMENT);
        ArithmeticExpressionNode expr = arithmetic_expression();
        consumeEOL();
        return new LetStatementNode(id, expr);
    }

    /**
     * method for a display statement definition
     * @return display statement node
     */
    //<Display_Stmt> ::= “display” id <EOL>
    public DisplayStatementNode display_statement(){
        match(TokenType.DISPLAY);
        String id = identifier();
        consumeEOL();
        return new DisplayStatementNode(id);
    }

    /**
     * method for an input statement definition
     * @return input statement node
     */
    //<Input_Stmt> ::= “input” id <EOL>
    public InputStatementNode input_statement(){
        match(TokenType.INPUT);
        String id = identifier();
        consumeEOL();
        return new InputStatementNode(id);
    }

    /**
     * method for an if statement definition
     * @return if statement node
     */
    public IfStatementNode if_statement(){
        match(TokenType.IF);
        BooleanExpressionNode condition = relational_expression();
        match(TokenType.COLON);
        match(TokenType.EOL);

        List<StatementNode> ifStatements = statement_block();
        List<StatementNode> elseStatements = remaining_if();
        return new IfStatementNode(condition,ifStatements,elseStatements);
    }

    /**
     * method for remaining if statement definition
     * @return statement_block()
     */
    public List<StatementNode> remaining_if(){
        if(current_token.get_type() == TokenType.ELSE){
            match(TokenType.ELSE);
            match(TokenType.COLON);
            match(TokenType.EOL);
            return statement_block();
        }
        else if(current_token.get_type() == TokenType.ELIF){
            match(TokenType.ELIF);
            BooleanExpressionNode elifCondition = relational_expression();
            match(TokenType.COLON);
            match(TokenType.EOL);
            List<StatementNode> elifStatements = statement_block();

            List<StatementNode> remainingElse = remaining_if();

            List<StatementNode> elseStatements = new ArrayList<>();
            elseStatements.add(new IfStatementNode(elifCondition,elifStatements,remainingElse));
            return elseStatements;
        }
        return null;
    }

    /**
     * method for a while statement definition
     * @return while statement node
     */
    public WhileStatementNode while_statement(){
        match(TokenType.WHILE);
        BooleanExpressionNode condition = relational_expression();
        match(TokenType.COLON);
        match(TokenType.EOL);
        List<StatementNode> statements = statement_block();
        return new WhileStatementNode(condition, statements);
    }

    /**
     * method for a for loop statement definition
     * @return for statement node
     */
    public ForStatementNode for_statement(){
        match(TokenType.FOR);
        String id = identifier();
        match(TokenType.IN);

        ArithmeticExpressionNode start = arithmetic_expression();
        match(TokenType.RANGE);
        ArithmeticExpressionNode end = arithmetic_expression();

        match(TokenType.COLON);
        match(TokenType.EOL);

        List<StatementNode> statements = statement_block();
        return new ForStatementNode(id, start, end, statements);
    }

    /**
     * method for a statement block definition
     * @return list of statements
     */
    public List<StatementNode> statement_block(){
        match(TokenType.INDENT);
        List<StatementNode> statements = new ArrayList<>();

        while(current_token.get_type() != TokenType.EOS && current_token.get_type() != TokenType.DEDENT){
            if(current_token.get_type() == TokenType.EOL){
                match(TokenType.EOL);
                continue;
            }

            statements.add(statement());
        }

        match(TokenType.DEDENT);
        return statements;
    }

    /**
     * method for a relational expression definition
     * @return relational expression node
     */
    public BooleanExpressionNode relational_expression(){
        ArithmeticExpressionNode left = arithmetic_expression();

        TokenType op = current_token.get_type();
        switch(op){
            case LESS_THAN:
            case LESS_EQUAL:
            case GREATER_THAN:
            case GREATER_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
                match(op);
                break;
            default:
                throw new InvalidParseException("Expected relational operator: " + current_token);
        }

        ArithmeticExpressionNode right = arithmetic_expression();
        return new RelationalExpressionNode(left, op, right);
    }

    /**
     * method for an expression definition
     * @return expression node
     */
    //<Arithmetic Expression> ::= <Term> <Expression_Prime>
    public ArithmeticExpressionNode arithmetic_expression(){
        ArithmeticExpressionNode termNode = term();
        return expression_prime(termNode);
    }

    /**
     * method for an expression prime definition, matches + or - operator
     * @param expr - expression node from expression()
     * @throws InvalidArgumentException if expr is null
     * @return BinaryExpressionNode if operator is + or -, else return expr
     */
    /*
    <Expression_Prime> ::= “+” <Term> <Expression_Prime>
                            | “-” <Term> <Expression_Prime>
                            | null
     */
    public ArithmeticExpressionNode expression_prime(ArithmeticExpressionNode expr) throws InvalidArgumentException{
        if (expr == null){
            throw new InvalidArgumentException("Null expr node in expression_prime().");
        }
        if(current_token.get_type() == TokenType.ADDITION){
            match(TokenType.ADDITION);
            TermNodeArithmetic termNode = term();
            BinaryExpressionNodeArithmetic addExpr = new BinaryExpressionNodeArithmetic(expr,TokenType.ADDITION,termNode);
            return expression_prime(addExpr);
        }
        else if(current_token.get_type() == TokenType.SUBTRACTION){
            match(TokenType.SUBTRACTION);
            TermNodeArithmetic termNode = term();
            BinaryExpressionNodeArithmetic subExpr = new BinaryExpressionNodeArithmetic(expr,TokenType.SUBTRACTION,termNode);
            return expression_prime(subExpr);
        }

        return expr;
    }

    /**
     * method for a term definition
     * @return TermNode from term_prime()
     */
    //<Term> ::= <Factor> <Term_Prime>
    public TermNodeArithmetic term(){
        FactorNodeArithmetic factorNode = factor();
        return term_prime(factorNode);
    }

    /**
     * method for a term prime definition, matches * or / operator
     * @param term TermNodeArithmetic from term()
     * @throws InvalidArgumentException if term is null
     * @return BinaryTermNode if operator is * or /, else return term
     */
    /*
    <Term_Prime> ::= “*” <Factor> <Term_Prime>
                    | “/” <Factor> <Term_Prime>
                    | null
     */
    public TermNodeArithmetic term_prime(TermNodeArithmetic term) throws InvalidArgumentException{
        if(term == null){
            throw new InvalidArgumentException("Null term node in term_prime().");
        }
        if(current_token.get_type() == TokenType.MULTIPLICATION){
            match(TokenType.MULTIPLICATION);
            FactorNodeArithmetic factor = factor();
            BinaryTermNodeArithmetic multTerm = new BinaryTermNodeArithmetic(term,TokenType.MULTIPLICATION,factor);
            return term_prime(multTerm);
        }
        else if(current_token.get_type() == TokenType.DIVISION){
            match(TokenType.DIVISION);
            FactorNodeArithmetic factor = factor();
            BinaryTermNodeArithmetic divTerm = new BinaryTermNodeArithmetic(term,TokenType.DIVISION,factor);
            return term_prime(divTerm);
        }

        return term;
    }

    /**
     * method for a factor definition, matches ( expr ), or -(unary)
     * @return ParenFactorNode if token type is paren
     * @return NegFactorNode if token type is unary -
     * @return IdentifierNode if token type is identifier
     * @return NumNode if token type is int lit
     */
    //<Factor> ::= “(“ <Expression> “)” | “-” <Expression> | <Number>
    public FactorNodeArithmetic factor(){
        if(current_token.get_type() == TokenType.LEFT_PAREN){
            match(TokenType.LEFT_PAREN);
            ArithmeticExpressionNode expr = arithmetic_expression();
            match(TokenType.RIGHT_PAREN);
            return new ParenFactorNode(expr);
        }
        else if(current_token.get_type() == TokenType.SUBTRACTION){
            match(TokenType.SUBTRACTION);
            FactorNodeArithmetic factorNode = factor();
            return new NegFactorNode(factorNode);
        }
        else if(current_token.get_type() == TokenType.ID){
            String id = identifier();
            return new IdentifierNode(id);

        }
        else{
            int intLit = number();
            return new NumNode(intLit);
        }
    }

    /**
     * method for an identifier definition
     * @return id as string
     */
    //<id> ::= letter<rest_id>
    //<rest_id> ::= letter<rest_id> | digit<rest_id> | letter | digit
    public String identifier(){
        String id = current_token.get_lexeme();
        match(TokenType.ID);
        return id;
    }
    /**
     * method for a number definition
     * @return integer form of token lexeme
     */
    //<Number> ::= Int-Lit
    public int number(){
        String num = current_token.get_lexeme();
        match(TokenType.INT_LIT);
        return Integer.parseInt(num);
    }

    private void consumeEOL() {
        if (current_token.get_type() == TokenType.EOL) {
            match(TokenType.EOL);
        }
    }
}
