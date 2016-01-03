package suffixTree;

public class CompactSuffixTree extends AbstractSuffixTree {

	public CompactSuffixTree(SimpleSuffixTree simpleSuffixTree) {
		super(simpleSuffixTree.text);
		super.root = compactNodes(simpleSuffixTree.root, 0);
	}

	private SuffixTreeNode compactNodes(SuffixTreeNode node, int nodeDepth) {
		node.nodeDepth = nodeDepth;
		for (SuffixTreeNode child : node.children) {
			while (child.children.size() == 1) {
				SuffixTreeNode grandchild = child.children.iterator().next();
				child.incomingEdge.label += ", " + grandchild.incomingEdge.label;
				child.stringDepth += grandchild.incomingEdge.label.length();
				child.children = grandchild.children;
				for (SuffixTreeNode grandchildAux : child.children)
					grandchildAux.parent = node;
			}
			child = compactNodes(child, nodeDepth + 1);
		}
		return node;
	}
}
