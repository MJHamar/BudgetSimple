package budget.backend.structures;

/**
 * Implementation of a Binary Tree Heap, which stores a set of {@link BinaryTreeNode<K,V>} objects. It also supports both Key-only and Key-Value trees, where the latter consists of a Comparable key, which is used as a pointer to another object, which is considered as value.
 */
public class BinaryTreeHeap<K extends Comparable<K>,V> extends BinaryTree<K,V>{

	/**
	 * The root node, from which any part of the tree is accessible with a path, which has worst-case length: (log_2 (n)), where n is the number of nodes in the tree
	 */
	private BinaryTreeNode<K,V> root;
	/**
	 * The last node of the tree, used when insertion or removal from the tree is executed, helps to maintain the structure of the tree, such that it always as balanced as possible
	 */
	private BinaryTreeNode<K,V> last;

	private int size;

	/**
	 * Set up an empty tree
	 */
	public BinaryTreeHeap(){
		root = null;
		//initialise the root of the superclass such that its methods can be applied in this class
		super.root = root;
		last = null;
		size = 0;
	}

	/** Definition of the abstract method in the superclass. Only to be used by insert */
	protected void add(K key) throws NullPointerException{
		try{
			//the the tree is empty, initialise a new tree			
			if (isEmpty()){
				root = new BinaryTreeNode<K,V>(key);
				//reset the root of the superclass
				super.root = root;
				last = root;
			}
			//add new node to the last element, find new last and sort the tree again
			else{
				findNext();				
				last.setKey(key);			
				upHeap(last);		
			}
			size++;
		} catch(NullPointerException e){
			throw e;
		}
			
	}

	/** Definition of the abstract method in the superclass. Only to be used by insert */
	protected void add(K key, V value) throws NullPointerException{
		try {
			// the the tree is empty, initialise a new tree
			if (isEmpty()) {
				root = new BinaryTreeNode<K, V>(key, value);
				// reset the root of the superclass
				super.root = root;
				last = root;
			}
			// add new node to the last element, find new last and sort the tree again
			else {
				findNext();
				last.setKey(key);
				last.setValue(value);
				upHeap(last);
			}
			size++;	
		} 
		catch (NullPointerException e) {
			throw e;
		}
	}

	/**
	 * insertion method to the Heap. Isert element to the current last node, find the next last node and upheap the inserted node until the properties of the heap hold.
	 * To be used when building Key-only trees
	 * @param key the key of the new leaf
	 */
	public void insert(K key){
		add(key);
	}

	/**
	 * insertion method to the Heap. Isert element to the current last node, find the next last node and upheap the inserted node until the properties of the heap hold.
	 * To be used when Key-Value trees
	 * @param key the key of the new leaf
	 * @param value the value of the new leaf
	 */
	public void insert(K key, V value){
		add(key, value);
	}

	/**
	 * @return the key of the minimum element of the tree, which is always in the root node, without removing it.
	 * @return null, if the tree is empty
	 */
	public K getMinK(){
		if (!(isEmpty()))
			return root.getKey();
		else return null;
	}

	/**
	 * @return the value of the minimum element of the tree, which is always in the root node, without removing it.
	 * @return null, if the tree is empty
	 */
	public V getMinV(){
		if (!(isEmpty()))
			return root.getValue();
		else return null;
	}

	/**
	 * Remove the minimum element from the heap, which is always the root node.
	 * Restore the heap order
	 * 
	 * @return the key of the minimum element
	 * @return null is the tree is empty
	 */
	public K removeMinK(){
		if (!isEmpty()){
			K ret = root.getKey();
			remove(root);
			return ret;
		}
		return null;
	}

	/**
	 * Remove the minimum element from the heap, which is always the root node.
	 * Restore the heap order
	 * @return the value of the minimum element
	 * @return null is the tree is empty
	 */
	public V removeMinV(){
		if (!isEmpty()) {
			V ret = root.getValue();
			remove(root);
			return ret;
		}
		return null;
	}

	protected boolean remove(BinaryTreeNode<K,V> node){
		if (!(isEmpty())){
			if (size == 1){
				node = null;
			}
			else {
				//Let the key of the root the most recently inserted item
				sw(node,last);

				//remove the last element from the tree, by setting the pointer from its parent to null
				BinaryTreeNode<K,V> parent = last.getParent();
				if (last.equals((parent.getRight())))
						parent.setRight(null);
				else parent.setLeft(null);

				//restore the Heap, such that it remains ordered.
				downHeap(root);
				findPrev();
			}
			size--;
			return true;
		}
		return false;
	}

	/**
	 * If the properties of the heap hold, the last node is always on the lowest level of the tree.
	 * Count the nodes in the path until the root from the last node.
	 * @return the height of the tree. A single leaf has a hegight of 0.
	*/
	public int height(){
		return super.height();
	}

	public int getSize(){
		return this.size;
	}

	/**
	 * Really expensive method in this implementaton, and should only be used when necessary.
	 * @return true if the tree contains the given node
	 * @return false otherwise
	 */
	public boolean contains(BinaryTreeNode<K,V> node){
		if (isEmpty())
			return false;
		else
			return searchTree(root, node);
	}

	/**
	 * Count the number of ancestors of a node recursively, until the root is found.
	 * @param node a {@link BinaryTreeNode}
	 * @return the number of ancestors. The root is on level 0, its children are on level 1 etc.
	 */
	public int onLevel(BinaryTreeNode<K,V> node){
		if (node.getParent() == null)
			return 0;
		else return (1+onLevel(node.getParent()));
	}

	/**
	 * @return whether there are any keys defined in the tree.
	 */
	public boolean isEmpty(){
		return size == 0;
	}

	/**
	 * Output the tree as a string in pre-order traversal
	 */
	public String toString(){
		return (preOrder(root));
	}

	/**
	 * Recursive implementation of upheaping; change the key of the parent and the child while the vlaue of the parent is >= than the key of the child.
	 * If the key already exists in the tree,
	 * @param leaf a {@link BinaryTreeNode} that has no children (on the first call)
	 * @return true if the upheap was successfull
	 * @return false otherwise; e.g. the key already existed in the tree
	 */
	private boolean upHeap(BinaryTreeNode<K,V> leaf){
		if (leaf.getParent() != null){
			if ((leaf.getKey()).compareTo((leaf.getParent()).getKey()) < 0){
				//switch the keys of the parent and the leaf
				sw(leaf.getParent(), leaf);
				//recursive call
				upHeap(leaf.getParent());

			} else if ((leaf.getKey()).compareTo((leaf.getParent()).getKey()) > 0)
				return true;
		}
		return false; //only possible if the key of the leaf equals to the key of the parent.
	}

	/**
	 * Implementation of downheap from the root. Switch the key of the smaller child and the parent, while the parent is bigger than or equal to the child
	 * @param subTree the root by default, then all the subtrees, in which a switch has been performed
	 */
	private void downHeap(BinaryTreeNode<K,V> subTree){
		//Avoiding compareTo errors
		if (subTree.isLeaf()){
			if ((subTree.getLeft() != null) && ((subTree.getKey()).compareTo((subTree.getLeft()).getKey()) > 0))
				sw(subTree, subTree.getLeft());
			else if ((subTree.getRight() != null) && ((subTree.getKey()).compareTo((subTree.getRight()).getKey()) > 0))
				sw(subTree, subTree.getRight());
		}
		//if subTree is not a leaf, switch with the smaller child (if any) and recuresively call downHeap with the child
		else {
			int which = ((subTree.getLeft()).getKey()).compareTo((subTree.getRight()).getKey());
			if ((which<0) && ((subTree.getKey()).compareTo((subTree.getLeft()).getKey()) > 0)){
				sw(subTree, subTree.getLeft());
				downHeap(subTree.getLeft());
			}
			else if ((subTree.getKey()).compareTo((subTree.getRight()).getKey()) > 0){
				sw(subTree, subTree.getRight());
				downHeap(subTree.getRight());
			}
		}
	}

	/**
 	 * switch the key of the two arguments
	 * @param p a {@link BinaryTreeNode}
	 * @param c a {@link BinaryTreeNode}
	 */
	private void sw(BinaryTreeNode<K,V> p, BinaryTreeNode<K,V> c){
		K keyClone = c.getKey();
		V valClone = c.getValue();

		c.setKey(p.getKey());
		c.setValue(p.getValue());
		p.setKey(keyClone);
		p.setValue(valClone);
	}

	private boolean searchTree(BinaryTreeNode<K,V> tree, BinaryTreeNode<K,V> leaf){
		if ((leaf.getKey().equals(tree.getKey())) && (leaf.getValue().equals(tree.getValue())))
			return true;
		if ((leaf.getKey()).compareTo(tree.getKey()) < 0)
			return false;
		return searchTree(tree.getLeft(), leaf) || searchTree(tree.getRight(), leaf);
	}

	/**
	 * Find the next last node of the tree. It can always be found by going up the tree until a left child or the root is found. If there is any, get the right child of the parent, then go down left until a leaf is found. A leaf has at least one empty child. Insert a new node that has the leaf as parent
	 */
	private void findNext(){
		BinaryTreeNode<K,V> ptr = last;

		//find a left child, or the root
		while ((ptr.getParent() != null) && (!(ptr.equals((ptr.getParent()).getLeft())))){
			ptr = ptr.getParent();
		}

		//find the right child of the parent, if there is a parent
		if (ptr.getParent() != null){
			if ((ptr.getParent()).getRight() != null)
		  	ptr = (ptr.getParent()).getRight();
			else {
				ptr = new BinaryTreeNode<K,V>(ptr.getParent(), null);
				(ptr.getParent()).setRight(ptr);
			}
		}

		if (ptr.getKey() != null){
			//find the leftmost leaf in the subtree
			while (!(ptr.isLeaf())){
				ptr = ptr.getLeft();
			}

			//return the left node if empty, return the right node otherwise.
			last = new BinaryTreeNode<K,V>(ptr, null);
			if (ptr.getLeft() == null){
				ptr.setLeft(last);
			}
			else {
				ptr.setRight(last);
			}
		} else last = ptr;

	}

	/**
	 * Reverse of the findNext algorithm, used to find the previous item, if the last was removed.
	 * @param parent the parent of the last node.
	 */
	private void findPrev(){
		//if the deleted node was a right child, then the next node is the left child.
		if ((last.getParent()).getLeft() != null) {
			last = (last.getParent()).getLeft();
		}
		else {
			last = last.getParent(); //if the left child was null, then the parent has no more children
			//find a right child, or the root
			while ((last.getParent() != null) && (!(last.equals((last.getParent()).getRight())))){
				last = last.getParent();
			}

			//go to the other sibling, if there is any
			if (last.getParent() != null)
				last = (last.getParent()).getLeft();

			//go down right until a leaf is found
			while(!(last.isLeaf())){
				last = last.getRight();
			}
		}
	}

	/**
	 * Make output, by recursively visiting all the nodes in pre-order traversal
	 */
	private String preOrder(BinaryTreeNode<K,V> n){
		String ret = "";
		if (n!=null){
			if (n.getValue() != null) ret += n.getValue();
			else ret += n.getKey();
			ret += ",";
			ret += preOrder(n.getLeft());
			ret += preOrder(n.getRight());
		}
		return ret;
	}
}
