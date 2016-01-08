package suffixTree;

import java.util.ArrayList;
import java.util.List;

public class SimpleSuffixTree extends AbstractSuffixTree {

	public SimpleSuffixTree(String text) {
		super(text);
		constructTree();
	}

	private void constructTree() {
		super.root = new SuffixTreeNode();
		char[] s = super.text.toCharArray();
		for (int i = 0; i < s.length; i++) {
			List<String> suffixList = new ArrayList<String>();
			for (int k = i; k < s.length; k++) {
				suffixList.add(s[k] + "");
			}
			super.root.addSuffix(suffixList, i + 1, i);
		}
	}

}
