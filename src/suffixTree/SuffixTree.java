package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("acbabababc"));
//		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("aaabbbc"));
//		String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
//		System.out.println("digraph {\n" + properties + tree.root + "}");
		System.out.println(tree.root);
	
		/*SimpleSuffixTree sTree = new SimpleSuffixTree("aaabbbc");
		System.out.println(sTree.root);*/
		
		String pattern = "aba";
		ArrayList<Integer> result = tree.searchAll(pattern);
		System.out.println(result);
		System.out.println("Num matches: " + result.size());
		System.out.println("Texto: " + tree.text);
		System.out.println("Patrón: " + pattern);
		
	}

}
