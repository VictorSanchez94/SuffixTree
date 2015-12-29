package utils;

public class Main {

	public static void main(String[] args){
		SuffixTree st = new SuffixTree("abcdefghijklmn");
		System.out.println(st.search("jk"));
	}
}