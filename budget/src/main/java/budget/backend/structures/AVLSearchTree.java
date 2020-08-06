package budget.backend.structures;


import java.util.ArrayList;

import budget.backend.structures.AVLTreeNode;

public class AVLSearchTree<K extends Comparable<K>, V> {

	/**  
	 * Pointer to the root of the tree. From this node, each element of the tree has to be accessible
	 */
	private AVLTreeNode<K,V> root;
	/**
	 * Empty node, which does not store any value, has no parent, or children and its height is -1. 
	 * Used to avoid null-pointer errors in the implementation
	 */
	private AVLTreeNode<K,V> empty;

	private int size;

	/**
	 * Initialise a new Tree instance, where root is null, and there is an 'empty' node, which has height -1. This is used to avoid null-pointer exceptions while managing a non-empty tree
	 */
	public AVLSearchTree(){
		root = null;
		empty = new AVLTreeNode<>();
		size = 0;
	}

	//Publicly accessbile methods

	/**
	 * Publicly accessible insertion method. Insertion is similar to a simple binary search tree, but once the addition is done, the structure of the tree will be restored if necessary
	 * This method takes at most O(log n) time
	 * For Key-only trees
	 * @param key a Comparable instance
	 * @return true if the insertion was successfull
	 * @return false in case a node with the given key already exists in the tree
	 */
	public boolean insert(K key){
		if (isEmpty()) {
			root = new AVLTreeNode<>(key);
			root.setLeft(empty);
			root.setRight(empty);
		} else {
			AVLTreeNode<K, V> n = findPlace(key, root);
			if (n.isEmpty()) return false;
			n.setLeft(empty);
			n.setRight(empty);
			updateHeight(n);
			reorderTree(n);
		}
		size++;
		return true;
	}

	/**
	 * When setting up a multi-map, it is good to be able to have a pointer to the last inserted node,
	 * and refer to it in other data structures. 
	 * Should be used with caution though, because this returns adirect reference to a part of the tree, and some of its methods can violate the properties of the Tree. 
	 * Proper encapsulation could solve this problem 
	 * @param key 
	 * @return the new AVLTreeNode
	 * @return the empty node if the insertion was unsuccessfull
	 */
	public AVLTreeNode<K,V> insertAndReturn(K key){
		AVLTreeNode<K,V> n;
		if (isEmpty()) {
			root = new AVLTreeNode<>(key);
			root.setLeft(empty);
			root.setRight(empty);
			n = root;
		} else {
			n = findPlace(key, root);
			if (n.isEmpty())
				return empty; //unsuccessfull insertion
			n.setLeft(empty);
			n.setRight(empty);
			updateHeight(n);
			reorderTree(n);
		}
		size++;
		return n;
	}

	/**
	 * insert a new node to the tree and set its value. 
	 * @see {@link insertAndReturn(K)} for further explanation
	 * @param key
	 * @param value
	 * @return the inserted AVLTreeNode if the insertion was successfull
	 * @return the empty node otherwise
	 */
	public AVLTreeNode<K,V> insertAndReturn(K key, V value){
		AVLTreeNode<K,V> n = insertAndReturn(key);
		if (!(n.isEmpty())) n.setValue(value);
		return n;
	}

	/**
	 * Publicly accessible insertion method. Insertion is similar to a simple binary search tree, but once the addition is done, the structure of the tree will be restored if necessary
	 * This method takes at most O(log n) time
	 * For Key-Value trees
	 * @param key a Comparable instance
	 * @return true if the insertion was successfull
	 * @return false otherwise
	 */
	public boolean insert(K key, V value){
		if (isEmpty()){
			root = new AVLTreeNode<>(key, value);
			root.setLeft(empty);
			root.setRight(empty);
		}
		else{
			AVLTreeNode<K,V> n = findPlace(key, root);
			if (n.isEmpty()) return false;
			n.setLeft(empty);
			n.setRight(empty);
			n.setValue(value);
			updateHeight(n);
			reorderTree(n);
		}
		size++;
		return true;
	}

	/**
	 * Remove a node from the tree with the given key
	 * @return the removed AVLTreeNode
	 */
	public AVLTreeNode<K,V> remove(K key){
		AVLTreeNode<K,V> node = findKey(key, root);
		AVLTreeNode<K,V> ext = empty;
		//two cases are considered: node is leaf or not
		if (!(node.isEmpty())){
			if (node.isLeaf())
				ext = removeLeaf(node);
			else{
				//initialise a pointer to the next element in an inorder traversal
				ext = nextLeftLeaf(node.getRight());
				removeLeaf(ext);
				//switch the external leaf and the node
				sw(node,ext);
			}
			reorderTree(ext);
			size--;
			return node;
		}
		return null;
	}

	/**
	 * Recursively apply binary search on the tree
	 * @param key the Comparable instance we are looking for
	 * @return true if there is a record in the tree with the given key
	 * @return false otherwise
	 */
	public boolean contains(K key){
		return (findKey(key, root) != null);
	}

	/**
	 * Apply binary search to the tree 
	 * @param key a Comparable instance
	 * @return the {@link AVLTreeNode} which matches the given key
	 * @return the {@link empty} node if the tree does not contain the given key
	 */
	public AVLTreeNode<K,V> find(K key){
		return findKey(key, root);
	}

	/**
	 * @return true if the tree does not contain any information
	 */
	public boolean isEmpty(){
		return (root == null);
	}

	/**
	 * @return the height of the tree
	 */
	public int height(){
		return (root.getSubtreeHeight());
	}

	public int getSize(){
		return this.size;
	}

	/**
	 * @return a String of all the stored values in an inorder traversal.
	 */
	public String toString(){
		String ret = "";
		ArrayList<V> helper = inOrder(root);
		for (int i = 0; i<helper.size(); i++){
			ret += helper.get(i);
		}
		return ret;
	}

	/**
	 * @return a ArrayList instance containing all the stored values in descending order of their keys
	 */
	public ArrayList<V> inOrder() {
		return inOrder(root);
	}

	/**
	 * @return a ArrayList instance containing all the stored values in pre-order of their keys
	 */
	public ArrayList<V> preOrder() {
		return preOrder(root);
	}

	//private methods

	/**
	 * Recursively find a path to a node on which the BinarySearch properties hold, and end on a node, which has at least one empty leaf. Initialise a new AVLTreeNode and set its parent. Set the node as the child of the parent
	 * @param key a Comparable instance
	 * @param from an AVLTreeNode object
	 * @return the new AVLTreeNode
	 */
	private AVLTreeNode<K,V> findPlace(K key, AVLTreeNode<K,V> from){
		//avoid null-pointer errors
		if (from.isLeaf()){
			//three cases are likely to occur, left is external, right is external, both are external.
			if (key.compareTo(from.getKey()) < 0){
				if (from.getLeft() == empty){
					AVLTreeNode<K,V> ret = new AVLTreeNode<K,V>(from, key);
					from.setLeft(ret);
					return ret;
				}
				else {
					return findPlace(key, from.getLeft());
				}
			}
			else if (key.compareTo(from.getKey()) > 0){
				if (from.getRight() == empty){
					AVLTreeNode<K,V> ret = new AVLTreeNode<K,V>(from, key);
					from.setRight(ret);
					return ret;
				}
				else 
					return findPlace(key, from.getRight());
			} else return empty; //equality is not permitted between keys
		}
		//find the next node in the binary search
		else {
			if (key.compareTo(from.getKey()) < 0){
				return findPlace(key, from.getLeft());
			} else if (key.compareTo(from.getKey()) > 0) return findPlace(key, from.getRight());
			else return empty; //equality is not permitted between keys
		}

	}

	/**
	 * Find the given key in the tree using binary search
	 * 
	 * @param key  a Comparable instance
	 * @param from an AVLTreeNode, which is the root of the subtree in which the
	 *             search is executed
	 * @return the AVLTreeNode which has the given key
	 * @return null if the search was unsuccessfull
	 */
	private AVLTreeNode<K, V> findKey(K key, AVLTreeNode<K, V> from) {
		if (from != null){
			if ((from.getLeft() == null) && (from.getRight() == null)) {
				return null;
			} else if (key.compareTo(from.getKey()) == 0) {
				return from;
			} else {
				if (key.compareTo(from.getKey()) < 0)
					return findKey(key, from.getLeft());
				else
					return findKey(key, from.getRight());
			}
		} return null;
	}

	/**
 	 * switch the key and value of the two arguments
	 * @param p a {@link BinaryTreeNode}
	 * @param c a {@link BinaryTreeNode}
	 */
	private void sw(AVLTreeNode<K,V> p, AVLTreeNode<K,V> c){
		K keyClone = c.getKey();
		V valClone = c.getValue();

		c.setKey(p.getKey());
		c.setValue(p.getValue());
		p.setKey(keyClone);
		p.setValue(valClone);
	}

	/**
	 * update the height with the given value of the ascending nodes from the given node recursively
	 * @param n by default, the parent of the newly inserted/deleted node
	 * @param byVal only two values are permitted: +1 and -1.
   */
  private void updateHeight(AVLTreeNode<K,V> n){
		
		if (n != null){
			updateNodeH(n);
			//recursive call
			updateHeight(n.getParent());
		}
		//exit at the root	
	}
	
	/**
	 * update the height of one node. It is always one more than its higher subtree
	 * @param n an AVLTreeNode
	 */
	private void updateNodeH(AVLTreeNode<K,V> n){
		int bigger = Math.max((n.getLeft()).getSubtreeHeight(), (n.getRight()).getSubtreeHeight());
		
		n.setSubtreeHeight(bigger+1);
	}
	
	/**
	 * Crawl up the tree from the given node and apply one of the four possible trasformations on each node that is unbalanced
	 * @param parent initially, the AVLTreeNode from which the rebalancing starts
	 */
	private void reorderTree(AVLTreeNode<K,V> parent){
		//Find the unbalanced grandchild by finding the unbalanced child first. With this method, we get three nodes and four other subtrees. From here, depending on the alignment of the three nodes, four different cases can occur
		AVLTreeNode<K,V> child = null;
		if (parent.balanceFactor() > 1){
			child = parent.getLeft();
			if (child.balanceFactor() > 0){
				child = case1(parent);
			}
			else{
				child = case3(parent);
				//update the height of the left child manually
				updateNodeH(child.getLeft());
			}
			if (child.getParent() != null){
				//if the handshake still holds between the previous parent and its parent, rewrite the pointer to the new parent
				if ((parent.getKey()).equals(((child.getParent()).getLeft()).getKey()))
					(child.getParent()).setLeft(child);
				else if ((parent.getKey()).equals(((child.getParent()).getRight()).getKey()))
					(child.getParent()).setRight(child);
			}
		}
		else if (parent.balanceFactor() < -1){
			child = parent.getRight();
			if (child.balanceFactor() > 0){
				child = case4(parent);
				//update the height of the right child manually
				updateNodeH(child.getRight());
			}
			else{
				child = case2(parent);
			}
			if (child.getParent() != null){
				//if the handshake still holds between the previous parent and its parent, rewrite the pointer to the new parent
				if ((parent.getKey()).equals(((child.getParent()).getLeft()).getKey()))
					(child.getParent()).setLeft(child);
				else if ((parent.getKey()).equals(((child.getParent()).getRight()).getKey()))
					(child.getParent()).setRight(child);
			}
		}	
		updateHeight(parent);
		if (child != null) parent = child;
		//stop at the root
		if (parent.getParent() != null){
			//recursive call
			reorderTree(parent.getParent());
		}
		else root = parent;
		
	}

	/**
	 * Remove an external node from the tree. if there is at most one, non-empty leaf, put it in its place
	 */
	private AVLTreeNode<K,V> removeLeaf(AVLTreeNode<K,V> node){
			if (((node.getLeft()).equals(empty)) && ((node.getRight()).equals(empty))){
				//both are empty, delete the node
				if (node.isLeft())
					(node.getParent()).setLeft(empty);
				else 
					(node.getParent()).setRight(empty);
		
			}
			else {
				AVLTreeNode<K,V> nonLeaf = null;
				//there is one that is not empty, put it in the place of node. Ensure the handshake between it and its parent
				if ((node.getLeft()).equals(empty)){
					nonLeaf = node.getRight();
				}
				else if ((node.getRight()).equals(empty)){
					nonLeaf = node.getLeft();
				}
				if (nonLeaf != null){
					if (node.isLeft())
						(node.getParent()).setLeft(nonLeaf);
					else 
						(node.getParent()).setRight(nonLeaf);
					nonLeaf.setParent(node.getParent());
				}
			}
			//refresh the heights of the subtree from which the removal happened
			updateHeight(node);
			return node;
	}

	/**
	 * find the leftmost descendant of the given node
	 * @param n an AVLTreeNode
	 * @return a leaf, that has no left child
	 */
	private AVLTreeNode<K,V> nextLeftLeaf(AVLTreeNode<K,V> n){
		if ((n.getLeft()).equals(empty))
			return n;
		return nextLeftLeaf(n.getLeft());
	} 

  /**
   * inbalance is caused by two consecutive left children (left child, left grandchild)
   * @param n the grandchild
   */
  private AVLTreeNode<K, V> case1(AVLTreeNode<K, V> n){
		//start from the parent. the child is left, and the grandchild is left
		//this method rewrites two and a half handshakes.
				
		n = n.getLeft();
		AVLTreeNode<K,V> subT = n.getRight();
		n.setRight(n.getParent());
		n.setParent((n.getRight()).getParent());//this handshake is not resolved here
		(n.getRight()).setParent(n);

		(n.getRight()).setLeft(subT);
		subT.setParent(n.getRight());
		//retrun the child. Do not worry about the last handshake here
		return n;
  }

  /**
   * inbalance is caused by two consecutive right children (right child, right grandchild)
   * @param n the grandchild
   */
  private AVLTreeNode<K, V> case2(AVLTreeNode<K, V> n){
    //start from the parent. the child is right, and the grandchild is right
		//this method rewrites two and a half handshakes.			
		n = n.getRight();
		AVLTreeNode<K,V> subT = n.getLeft();
		n.setLeft(n.getParent());
		n.setParent((n.getLeft()).getParent());//this handshake is not resolved here
		(n.getLeft()).setParent(n);

		(n.getLeft()).setRight(subT);
		subT.setParent(n.getLeft());
		//retrun the child. Do not worry about the handshake here
		return n;
  }

  /**
   * inbalance is caused by a left child and its right child
   * @param n the grandchild
   */
  private AVLTreeNode<K, V> case3(AVLTreeNode<K, V> n){
		n = (n.getLeft()).getRight();
		// inserting child to the right child of grandchild
		AVLTreeNode<K, V> subT = n.getRight();
		n.setRight((n.getParent()).getParent());

		(n.getRight()).setLeft(subT);
		subT.setParent(n.getRight());

		subT = n.getLeft();
		n.setLeft(n.getParent());

		(n.getLeft()).setRight(subT);
		subT.setParent(n.getLeft());

		n.setParent((n.getRight()).getParent());
		(n.getLeft()).setParent(n);
		(n.getRight()).setParent(n);
		return n;
  }

  /**
   * inbalance is caused by a right child and its left child
   * @param n the grandchild
   */
  private AVLTreeNode<K, V> case4(AVLTreeNode<K, V> n){
    n = (n.getRight()).getLeft();
		//inserting child to the right child of grandchild
		AVLTreeNode<K,V> subT = n.getLeft();
		n.setLeft((n.getParent()).getParent());

		(n.getLeft()).setRight(subT);
		subT.setParent(n.getLeft());

		subT = n.getRight();
		n.setRight(n.getParent());

		(n.getRight()).setLeft(subT);
		subT.setParent(n.getRight());

		n.setParent((n.getLeft()).getParent());
		(n.getLeft()).setParent(n);
		(n.getRight()).setParent(n);
		return n;
	}
	

	private ArrayList<V> preOrder(AVLTreeNode<K, V> n) {
		ArrayList<V> ret = new ArrayList<>();
		if ((n != null) && (!(n.isEmpty()))) {
			ret.add(n.getValue());
			ret.addAll(preOrder(n.getLeft()));
			ret.addAll(preOrder(n.getRight()));
		}
		return ret;
	}

	/**
   * fill sortedArray with the values stored in the tree in in-order traversal
   */
  private ArrayList<V> inOrder(AVLTreeNode<K,V> n){
		ArrayList<V> ret = new ArrayList<>();
		if ((n != null) && (!(n.isEmpty()))){
			ret.addAll(inOrder(n.getLeft()));
			ret.add(n.getValue());
			ret.addAll(inOrder(n.getRight()));
		}
		return ret;
	}	



}
