package budget.backend.structures;

import budget.backend.structures.BinaryTreeNode;

public abstract class BinaryTree<K,V>{

	/**
	 * Define a BinaryTreeNode as the root.
	 */
	protected BinaryTreeNode<K,V> root;

	/**
	 * Initialise the root.
	 */
	public BinaryTree(){
		root = null;
	}

	/**
	 * Add a new node with the given key to the binary tree.
	 * This method is unique to all implementations, therefore it should newer be used directly.
	 * @param key
	 */
	protected abstract void add(K key);

	/**
	 * Add a new node with the given key and value to the binary tree.
	 * This method is unique to all implementations, therefore it should newer be used directly.
	 * @param key
	 */
	protected abstract void add(K key, V value);

	/**
	 * Remove a node from the tree, if it exists.
	 * This method is unique to all implementations, therefore it should newer be used directly.
	 * @param node a BinaryTreeNode
	 * @return true if the node was successfully removed
	 * @return false if the removal was not successfull, or the node was not found in the tree.
	 */
	protected abstract boolean remove(BinaryTreeNode<K,V> node);

	/**
	 * Find a node in the tree
	 * @param node a BinaryTreeNode
	 * @return true if the node was found
	 * @return fale otherwise
	 */
	public abstract boolean contains(BinaryTreeNode<K,V> node);

	/**
	 * @return true if the tree contains no objects
	 * @return false otherwise
	 */
	public boolean isEmpty(){
		return (root == null);
	}

	/**
	 * @return the height of the tree. 0 means there is only one node, which is the root. -1 means there are no nodes initialised
	 */
	public int height(){
		return recHeight(root);
	}

	/**
	 * @return a String containing all nodes in the tree (keys/values or both) in an arbitrary traversal
	 */
	public abstract String toString();

	/**
	 * Recursively visit all the nodes in the sub-tree and count them.
	 * @param from the root of the sub-tree we are inspecting
	 * @return the height of the tree.
	 */
	private int recHeight(BinaryTreeNode<K,V> from){
		if (from == null)
			return -1;
		else
			return (1 + Math.max(recHeight(from.getLeft()), recHeight(from.getRight())));
	}


}
