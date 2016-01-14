package suffixTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SuffixTreeNode {

	SuffixTreeEdge incomingEdge = null;
	int nodeDepth = -1;
	int label = -1;
	Collection<SuffixTreeNode> children = null;
	SuffixTreeNode parent = null;
	int stringDepth;
	int id = 0;
	public static int c;
	
	int charPosition;		// character's position in the text
	HashSet<Integer> docsNode;

	public SuffixTreeNode(SuffixTreeNode parent, String incomingLabel, int depth, int label, int id, int acumulatedLength, int numDoc) {
		children = new ArrayList<SuffixTreeNode>();
		incomingEdge = new SuffixTreeEdge(incomingLabel, label);
		nodeDepth = depth;
		this.label = label;
		this.parent = parent;
		stringDepth = parent.stringDepth + incomingLabel.length();
		this.id = id;
	
		this.charPosition = acumulatedLength+depth;
		this.docsNode = new HashSet<Integer>();
		this.docsNode.add(numDoc);
	}

	public SuffixTreeNode() {
		children = new ArrayList<SuffixTreeNode>();
		nodeDepth = 0;
		label = 0;
		docsNode = new HashSet<Integer>();
	}

	public void addSuffix(List<String> suffix, int pathIndex, int acumulatedLength, int numDoc, boolean addingText) {
		SuffixTreeNode insertAt = this;
		if(addingText){
			insertAt = search(this, suffix, numDoc);
		}else{
			insertAt = search(this, suffix);
		}
		insert(insertAt, suffix, pathIndex, acumulatedLength, numDoc);
	}

	private SuffixTreeNode search(SuffixTreeNode startNode, List<String> suffix) {
		if (suffix.isEmpty()) {
			throw new IllegalArgumentException(
					"Empty suffix. Probably no valid simple suffix tree exists for the input.");
		}
		Collection<SuffixTreeNode> children = startNode.children;
		for (SuffixTreeNode child : children) {
			if (child.incomingEdge.label.equals(suffix.get(0))) {
				suffix.remove(0);
				if (suffix.isEmpty()) {
					return child;
				}
				return search(child, suffix);
			}
		}
		return startNode;
	}
	
	private SuffixTreeNode search(SuffixTreeNode startNode, List<String> suffix, int numDoc) {
		if (suffix.isEmpty()) {
			throw new IllegalArgumentException(
					"Empty suffix. Probably no valid simple suffix tree exists for the input.");
		}
		Collection<SuffixTreeNode> children = startNode.children;
		for (SuffixTreeNode child : children) {
			if (child.incomingEdge.label.equals(suffix.get(0))) {
				suffix.remove(0);
				child.docsNode.add(numDoc);
				if (suffix.isEmpty()) {
					return child;
				}
				return search(child, suffix,numDoc);
			}
		}
		return startNode;
	}

	private void insert(SuffixTreeNode insertAt, List<String> suffix, int pathIndex, int acumulatedLength, int numDoc) {
		for (String s:suffix) {
			SuffixTreeNode child = new SuffixTreeNode(insertAt, s, insertAt.nodeDepth + 1, pathIndex, id, acumulatedLength, numDoc);
	
			insertAt.children.add(child);
			insertAt = child;
			
		}
		
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String incomingLabel = this.isRoot() ? "" : this.incomingEdge.label;
		for (int i = 1; i < this.nodeDepth; i++)
			result.append("\t");
		if (this.isRoot()) {
			c = 1;
			this.id = 1;
		} else {
			this.id = c;
			result.append(this.parent.id + " -> ");
			result.append(this.id + "[label=\"" + incomingLabel + "\"] " + " position: " + this.charPosition + "  " + docsNode + "\n");
		}
		for (SuffixTreeNode child : children) {
			c++;
			child.id = c;
			result.append(child.toString());
		}
		return result.toString();
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	public Collection<SuffixTreeNode> getChildren() {
		return children;
	}
}
