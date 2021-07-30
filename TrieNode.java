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
public class TrieNode {
    TrieNode children [] = new TrieNode[26];
    boolean isTerminal;
    
    public TrieNode(){
        isTerminal = false;
        
        for(int i=0; i<26; i++){
            children[i]=null;
        }
    }
}
