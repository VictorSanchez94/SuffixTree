package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("aaabbbc"));
//		String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
//		System.out.println("digraph {\n" + properties + tree.root + "}");

		System.out.println(tree.root);
	
		
		ArrayList<Integer> result = tree.searchAll("aab");
		System.out.println(result);
		
	}

}
