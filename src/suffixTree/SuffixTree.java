package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

//		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("asdfasdfasdfasdfasdfqwerqwerqwer"));
//		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("aaabbbcccccc"));
//		String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
//		System.out.println("digraph {\n" + properties + tree.root + "}");
//		System.out.println(tree.root);
	
		SimpleSuffixTree sTree = new SimpleSuffixTree("aaabbbc");
		sTree.addText("aaab", 2);
		sTree.addText("abb", 3);
		System.out.println(sTree.root);
		
		CompactSuffixTree tree = new CompactSuffixTree(sTree);
		System.out.println(tree.root);
		
		String pattern = "abb";
		ArrayList<Integer> result = tree.searchAll(pattern, true);
		System.out.println(result);
		System.out.println("Num matches: " + result.size());
		System.out.println("Text: " + tree.text);
		System.out.println("Pattern: " + pattern);
		
	}

}
