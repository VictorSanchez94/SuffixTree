package suffixTree;

public class AbstractSuffixTree {

	String text = null;
	SuffixTreeNode root = null;
	int inputAlphabetSize = -1;

	AbstractSuffixTree(String text) {
		if (text.length() > 0 && text.charAt(text.length() - 1) == '$') {
			this.text = text;
		} else {
			this.text = text + "$";
		}
	}

}
