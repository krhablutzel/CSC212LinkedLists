package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.*;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor and predecessor.
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		T removed = start.value;
		start = start.after;
		if (start == null) {
			// empty now
			end = null;
		} else {
			// nothing before
			start.before = null;
		}
		return removed;	
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T removed = end.value;
		end = end.before;
		if (end == null) {
			// empty now
			start = null;
		} else {
			// nothing after
			end.after = null;
		}
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
		// special case: removing from the front
		if (index == 0) {
			return removeFront();
		}
		
		int at = 0;
		for (Node<T> n = start; n != null; n = n.after) {
			// if we're at the index
			if (at++ == index) {
				// special case: removing from the back
				if (n == end) {
					return removeBack();
				}
				
				// normal case
				T removed = n.value;
				n.before.after = n.after;
				n.after.before = n.before;
				return removed;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> second = start;
			start = new Node<T>(item);
			start.after = second;
			second.before = start;
		}
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		// special case: adding to the front
		if (index == 0) {
			addFront(item);
			return;
		}
		
		// otherwise, must not be an empty list
		checkNotEmpty();
		
		// normal case
		int at = 0;
		for (Node<T> n = start; n != null; n = n.after) {
			// if we're at the index
			if (at++ == index) {
				Node<T> added = new Node<T>(item);
				// fix links to the left
				added.before = n.before;
				added.before.after = added;
				// fix links to the right
				added.after = n;
				n.before = added;
				return;
			}
		}
		
		// special case: adding to the back
		if (at == index) {
			addBack(item);
			return;
		}
		
		throw new BadIndexError(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return end.value;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = start; n != null; n = n.after) {
			// if we're at the index
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = start; n != null; n = n.after) {
			// if we're at the index
			if (at++ == index) {
				n.value = value;
				return;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
}
