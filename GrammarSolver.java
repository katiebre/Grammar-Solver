//Blake Denniston
//CSE 143 Section BK - Hunter Schafer
//
//Description: This is a program that manipulates a given grammar BNF input file
//and generates sentences according to the file's written rules.

import java.util.*;
public class GrammarSolver {
   private Map<String, List<String>> ruleMap;
   private Random r;
   
   //pre: If list is null or a size of zero, throws an IllegalArgumentException.
   //Takes in a list of strings-
   //If there are repeated nonterminals passed in, throws an IllegalArgumentExcecption.
   //post: separates the nonterminal and terminal rules in sorted order
   public GrammarSolver(List<String> rules) {
      if(rules == null || rules.size() == 0) {
         throw new IllegalArgumentException();
      }
      ruleMap = new TreeMap<String, List<String>>();
      r = new Random();
      
      for(String s : rules) { 
         String[] data = s.split("::=");
         if(ruleMap.containsKey(data[0])) {
            throw new IllegalArgumentException();
         }
         
         String[] terminal = data[1].split("[|]");
                 
         List<String> terminalList = new ArrayList<String>();
         for(String t : terminal) {
            String test = t.trim();
            terminalList.add(test);
         }
         ruleMap.put(data[0], terminalList);
      }
   }
   
   //pre: if symbol is null or if symbol is empty, throws IllegalArgumentException
   //post: returns true if the String passed in exists
   //    : returns false if the String passed in does not exist
   public boolean contains(String symbol) {
      validSymbol(symbol);
      return ruleMap.containsKey(symbol);
   }
   
   //returns a set of non-terminals of type String
   public Set<String> getSymbols() {
      return ruleMap.keySet();
   }
   
   //pre : symbol must be a nonterminal - checks in the generateRecursion method,
   //      otherwise returns it
   //    : if symbol is null or empty, throw IllegalArgumentException
   //    : takes in a String as a parameter
   //post: returns a String as the randomly generated occurance of a symbol
   public String generate(String symbol) {
      String result = generateRecursion(symbol);
      return result.substring(0, result.length() - 1);
   }


   //pre : throws an IllegalArgumentException if the passed in String parameter
   //      is null or empty
   //post: if passed in String is not a nonterminal, returns it
   //    : returns a randomly generated occurance of symbol and return it as a String
   private String generateRecursion(String symbol) {
      validSymbol(symbol);
      if(!ruleMap.containsKey(symbol)) {
         return symbol;
      }
      String result = "";
      //get a rule as a list
      List<String> terminals = ruleMap.get(symbol);
      //pick one rule at random
      String chosenRule = terminals.get(r.nextInt(terminals.size()));
      //remove spaces
      String[] ruleArray = chosenRule.split("[ \t]+");
      
      if(ruleArray.length > 1) {
         for(String s : ruleArray) {
            if (ruleMap.containsKey(s)) {
               result += generateRecursion(s);
            } 
            else {
               result += s + " ";
            }
         }
      } 
      else if (ruleMap.containsKey(chosenRule)){
         result += generateRecursion(chosenRule);
      } 
      else {
         result += generateRecursion(chosenRule) + " ";
      }      
      return result;
   }


   //private helper method.  If symbol is not valid or if symbol is empty, then throws an
   //IllegalArgumentException
   private void validSymbol(String symbol) {
      if(symbol == null || symbol.length() == 0) {
         throw new IllegalArgumentException();
      }
   }
}