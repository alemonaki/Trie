/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw1;
//package Hw1;
/**
 *
 * @author alkis
 */
public class Trie {
    TrieNode root;
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    public Trie(String [] words){
        root = new TrieNode();
        
        for(int i=0; i<words.length; i++){
            if(words[i] == null){
                break;
            }
            add(words[i]);
        }
    }
    
    public boolean add(String word){
        int layer;
        TrieNode start = root;
        int length = word.length();
        int position;       
        
        if(contains(word)==true){
            return(false);
        }
        
        for(layer=0; layer<length; layer++){
            position = word.charAt(layer) - 'a';
            
            if(start.children[position]==null){
                start.children[position] = new TrieNode();
            }
            start = start.children[position];
        }
        start.isTerminal = true;
        return(true);
    }
    
    public boolean contains(String word){
        int layer;
        TrieNode start = root;
        int length = word.length();
        int position;
        
        for(layer=0; layer<length; layer++){
           position = word.charAt(layer) - 'a';
           
           if(start.children[position]==null){
               return(false);
           }
           start = start.children[position];
        }
        return(start.isTerminal);
    }
    
    public int size(){
        TrieNode start = root;
        int result;
        
        result = sizeRecursion(start);
        
        return(result);
    }
    
    public int sizeRecursion(TrieNode start){
        int res=0, i;
        
        if(start.isTerminal == true){
            res = res + 1;
        }
        
        for(i=0; i<26; i++){
            if(start.children[i] != null){
                res = res + sizeRecursion(start.children[i]);
            }
        }
        return(res);
    }
    
    public String [] wordsOfPrefix(String prefix){
        TrieNode start = root;
        
        for(int layer=0; layer<prefix.length(); layer++){
            int position = prefix.charAt(layer) - 'a';
            
            if(start.children[position]==null){
                return null;
            }
            start=start.children[position];
        }
        
        int table_size = sizeRecursion(start);
        
        String [] table = new String[table_size];
        table = findWords(prefix, start, table, table_size);
        
        return(table);
    }
    
    public String [] findWords(String prefix, TrieNode start, String [] table, int length){
        
        if(start==null){
            return(table);
        }
        if(start.isTerminal==true){
            for(int j=0; j<length; j++){
                if(table[j]==null){
                    table[j] = prefix;
                    break;
                }
            //System.out.println(prefix);
            }
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i] != null){
                findWords(prefix + (char)('a' + i), start.children[i], table, length);
            }
        }
        
        return(table);
    }
    
    @Override
    public String toString(){
        TrieNode start = root;
        StringBuilder str = new StringBuilder("");
        String result;
        
        result = display(start, (char)0, str);
        
        return(result);
    }
    
    public String display(TrieNode start, char letter, StringBuilder str){
        
        if(start==null){
            return(str.toString());
        }
        if(start!=root){
            if(start.isTerminal==true){
                str.append(" ").append(letter).append("!");               
            }
            else{
                str.append(" ").append(letter);           
            }
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i]!=null){
                display(start.children[i], (char)('a'+i), str);
            }
        } 
        return(str.toString());
    }
    
    public String[] differByOne(String word){
        String [] table;
        String diffword = "";
        int mistakesAllowed = 0, level = -1, pos = 0;
        TrieNode start = root;
        
        table = new String[sizeoftable(start, word.length(), -1)];
        table = diffbyone(start, mistakesAllowed, table, word, level, pos, diffword);
        return(table);
    }
    
    public String[] diffbyone(TrieNode start, int mistakesAllowed, String [] table, String word, int level, int pos, String diffword){
        
        if(start==null){
            return(table);
        }
        if((level == word.length()-1) && (word.charAt(level) - 'a') != pos){
             mistakesAllowed = mistakesAllowed + 1;
        }
        if((level == word.length()-1) && mistakesAllowed==1 && start.isTerminal==true){
            for(int j=0; j<size(); j++){
                if(table[j]==null){
                    table[j] = diffword;
                    break;
                }
            }
        }
        
        if( level >= 0 && (word.charAt(level) - 'a') != pos){
            mistakesAllowed = mistakesAllowed + 1;
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i]!=null && mistakesAllowed <= 1 && (level < word.length()-1)){
                diffbyone(start.children[i], mistakesAllowed, table, word, level+1, i, diffword + (char)('a'+i));
                
            }
        }
        return(table);
    }
    
    public String[] differBy(String word, int max){
        String [] table;
        String diffword = "";
        int mistakesAllowed = 0, level = -1, pos = 0;
        TrieNode start = root;
        
        table = new String[sizeoftable(start, word.length(), -1)];
        //System.out.println(sizeoftable(start, word.length(), -1));
        table = diffbymax(start, mistakesAllowed, table, word, level, pos, diffword, max);
        return(table);
    }
    
    public String[] diffbymax(TrieNode start, int mistakesAllowed, String [] table, String word, int level, int pos, String diffword, int max){
        
        if(start==null){
            return(table);
        }
        if((level == word.length()-1) && (word.charAt(level) - 'a') != pos){
             mistakesAllowed = mistakesAllowed + 1;
        }
        if((level == word.length()-1) && mistakesAllowed<=max && start.isTerminal==true){
            for(int j=0; j<size(); j++){
                if(table[j]==null){
                    table[j] = diffword;
                    break;
                }
            }
        }
        
        if( level >= 0 && (word.charAt(level) - 'a') != pos){
            mistakesAllowed = mistakesAllowed + 1;
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i]!=null && mistakesAllowed <= max && (level < word.length()-1)){
                diffbymax(start.children[i], mistakesAllowed, table, word, level+1, i, diffword + (char)('a'+i), max);
                
            }
        }
        return(table);
    }
    
    public int sizeoftable(TrieNode start, int length,int level){
        int res=0;
        
        if(start.isTerminal==true && level==length-1){
            res = res + 1;
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i]!=null && level<(length-1)){
                res = res + sizeoftable(start.children[i], length, level+1);
            }
        }
        return(res);
    }
    
    public String toDotString(){
        TrieNode start = root;
        StringBuilder str = new StringBuilder("graph Trie {");
        String result;
        str.append("\n");
        result = display_dot(start, (char)0, str, 0);
        result = result + "\n" + "}";
        
        return(result);
    }
    
    public String display_dot(TrieNode start, char letter, StringBuilder str, int hash){
        String label = " [label=\"";
        String red = "\" ,shape=circle, color=red]";
        String black = "\" ,shape=circle, color=black]";
        if(start==null){
            return(str.toString());
        }
        int hashcode = start.hashCode();
        if(start == root){
            str.append("\t").append(hashcode);
            str.append(" [label=\"ROOT\" ,shape=circle, color=black]");
        }
        else{
            str.append("\n").append("\t").append(hash).append(" -- ").append(hashcode).append("\n").append("\t").append(hashcode).append(label).append(letter);
            if(start.isTerminal==true){
                str.append(red);
            }
            else{               
                str.append(black);           
            }
        }
        
        for(int i=0; i<26; i++){
            if(start.children[i]!=null){
                display_dot(start.children[i], (char)('a'+i), str, hashcode);
            }
        } 
        return(str.toString());
   }
    
}
