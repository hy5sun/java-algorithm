import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		int count = Integer.parseInt(br.readLine().trim());

		Map<String, TreeNode> nodeMap = new HashMap<>();
		TreeNode rootNode = null;

		for (int idx = 0; idx < count; idx++) {
			st = new StringTokenizer(br.readLine().trim());

			String root = st.nextToken();
			String left = st.nextToken();
			String right = st.nextToken();

			nodeMap.putIfAbsent(root, new TreeNode(root));
			TreeNode rootTree = nodeMap.get(root);

			if (idx == 0) {
				rootNode = rootTree;
			}

			if (!left.equals(".")) {
				nodeMap.putIfAbsent(left, new TreeNode(left));
				rootTree.setLeft(nodeMap.get(left));
			} else {
				rootTree.setLeft(null);
			}

			if (!right.equals(".")) {
				nodeMap.putIfAbsent(right, new TreeNode(right));
				rootTree.setRight(nodeMap.get(right));
			} else {
				rootTree.setRight(null);
			}
		}

		preOrder(rootNode);
		sb.append("\n");

		inOrder(rootNode);
		sb.append("\n");

		postOrder(rootNode);
		sb.append("\n");

		System.out.println(sb.toString());
	}

	static void preOrder(TreeNode node) {
		if (node == null) return;

		sb.append(node.value);

		preOrder(node.left);

		preOrder(node.right);
	}

	static void inOrder(TreeNode node) {
		if (node == null) return;

		inOrder(node.left);

		sb.append(node.value);

		inOrder(node.right);
	}

	static void postOrder(TreeNode node) {
		if (node == null) return;

		postOrder(node.left);

		postOrder(node.right);

		sb.append(node.value);
	}

	static class TreeNode {
		String value;
		TreeNode left;
		TreeNode right;

		public TreeNode(String value) {
			this.value = value;
		}

		public void setLeft(TreeNode left) {
			this.left = left;
		}

		public void setRight(TreeNode right) {
			this.right = right;
		}
	}
}
