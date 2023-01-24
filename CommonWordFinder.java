import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
/**
 * Class to find the n most common words in a document
 * @author Brayan Pichardo
 * @UNI byp2104
 * @version 1.0 December 13, 2022
 *
 * @resources
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html">...</a>
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/String.html">...</a>
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/io/File.html">...</a>
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html">...</a>
 */
public class CommonWordFinder {
    static int limit = 10;
    static File myFile;
    static MyMap<String, Integer> map;
    private static int unique_items = 0;

    public static void main(String[] args) {

        if (args.length < 2 || args.length > 3){
            System.err.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
            System.exit(1);
        }

        myFile = new File(args[0]);
        if (!myFile.exists()){
            System.err.println("Error: Cannot open file '" + args[0] + "' for input.");
            System.exit(1);
        }

        String DS = args[1];
        if ( !(DS.equals("bst") || DS.equals("avl") || DS.equals("hash")) ){
            System.err.println("Error: Invalid data structure '" + args[1] + "' received.");
            System.exit(1);
        }

        if (args.length == 3){
            try{
                if (Integer.parseInt(args[2]) < 1){
                    System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                    System.exit(1);
                }
            }
            catch(NumberFormatException e){
                System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                System.exit(1);
            }
            limit = Integer.parseInt(args[2]);
        }

        if (DS.equals("bst")){
            map = new BSTMap<>();
        }
        else if (DS.equals("avl")){
            map = new AVLTreeMap<>();
        }
        else{map = new MyHashMap<>();}

        parse(args[0]);
        mapRepresentation();
    }

    /**
     * Displays the content of the specified data structure
     */
    public static void mapRepresentation(){
        pair[] table = entryTable(map);
        int maxIndex;
        for (maxIndex = table.length - 1; maxIndex >= 0; maxIndex--) {
            if (table[maxIndex] != null) {
                break;
            }
        }
        int maxIndexWidth = String.valueOf(maxIndex).length();
        StringBuilder builder = new StringBuilder();
        String newLine = System.lineSeparator();
        builder.append("Total unique words: ").append(unique_items);
        builder.append(newLine);
        int upTo = Math.min(limit, table.length);
        for (int i = 0; i < upTo; i++) {
            int indexWidth = String.valueOf(i+1).length();
            builder.append(" ".repeat(maxIndexWidth - indexWidth));
            builder.append(i+1);
            builder.append(". ");
            builder.append(String.format("%-" +
                    calculateLongestWord(table,upTo) +
                    "s ", table[i].getWord()));
            builder.append(table[i].getValue());
            builder.append(newLine);
        }
        System.out.print(builder);
    }
    /**
     * A method that parses through a text file and puts words into the map.
     * @param arg the text file to be parsed
     */
    private static void parse(String arg){
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader in = new BufferedReader(new FileReader(myFile));
            while (in.ready()){
                char character = Character.toLowerCase((char)in.read());
                //checks for valid characters
                if (isValidCharacter(character)){
                    if (character == '-'){
                        if (!builder.isEmpty()){
                            builder.append(character);
                        }
                    }
                    else{builder.append(character);}
                }
                else if (isWhiteSpace(character) && !builder.isEmpty()){
                    if (map.get(String.valueOf(builder)) != null){
                        map.put(String.valueOf(builder),map.get(String.valueOf(builder))+1);
                    }
                    else{
                        if (!builder.isEmpty()){
                            map.put(String.valueOf(builder),1);
                        }
                    }
                    builder = new StringBuilder();
                }
            }
            if (!builder.isEmpty()){
                if (map.get(String.valueOf(builder)) != null){
                    map.put(String.valueOf(builder),map.get(String.valueOf(builder))+1);
                }
                else{
                    if (!builder.isEmpty()){
                        map.put(String.valueOf(builder),1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: An I/O error occurred reading '" + arg + "'.");
        }
    }

    /**
     * @param symbol the character being checked .
     * @return true if character is a representation of white space.
     */
    private static boolean isWhiteSpace(char symbol) {
        return symbol == ' ' || symbol == '\t' || symbol == '\n';
    }

    /**
     * Determines if input is a letter of the alphabet, a dash, or a semi-colon.
     * @param c the character to be checked.
     * @return true if the character is a member of the alphabet, a dash, or a hiphen.
     */
    private static boolean isValidCharacter(char c){
        return (( (c >= 'a' && c <= 'z')) || (c == '-') || (c == '\'') );
    }

    /**
     * This is a method that creates an array of key-value pairs obtained from the map
     * @param map the map to be represented as table
     * @return an array of pairs
     */
    private static pair[] entryTable(MyMap<String, Integer> map){
        Iterator<Entry<String, Integer>> iter = map.iterator();
        pair[] table = new pair[map.size()];
        int i = 0;
        while(iter.hasNext()){
            Entry<String,Integer> obj = iter.next();
            table[i] = new pair(obj.key, obj.value);
            unique_items++;
            i++;
        }
        Arrays.sort(table);
        return table;
    }

    /**
     * This method calculates the longest word in an array of pair objects and returns the legth
     * of the longest word
     * @param table an array of pair objects that is used to find the lowest word in the map
     * @param upTo
     * @return
     */
    private static int calculateLongestWord(pair[] table, int upTo){
        String n = table[0].getWord();
        for (int i=0; i < upTo; i++){
            if (table[i].getWord().length() > n.length()){
                n = table[i].getWord();
            }
        }
        return n.length();
    }
        /**
         * Class for encapsulating a key-value entry in a map.
         */
    private static class pair implements Comparable<pair>{
        String word;
        int value;
        pair(String word, int value){
            this.word =  word;
            this.value = value;
        }
        public String getWord(){return word; }
        public int getValue(){return value;}

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * @param o1 the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         */
        @Override
        public int compareTo(pair o1) {
            if (o1.getValue() == this.getValue()){
                return  this.getWord().compareTo(o1.getWord());
            }
            return o1.getValue()-this.getValue();
        }
    }
}

