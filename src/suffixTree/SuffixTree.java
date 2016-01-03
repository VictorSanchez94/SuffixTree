package suffixTree;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

		CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree("bananas"));
		String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
		System.out.println("digraph {\n" + properties + tree.root + "}");

	}

}
