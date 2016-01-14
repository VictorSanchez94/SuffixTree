package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {
	
		SimpleSuffixTree sTree = new SimpleSuffixTree("aa");
		sTree.addText("aaaaaac", 2);
		sTree.addText("aacb", 3);
		System.out.println(sTree.root);
		System.out.println("\n============================================================================================\n");
		CompactSuffixTree cTree = new CompactSuffixTree(sTree);
		System.out.println(cTree.root);
		
	}

}
