package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		// save removed value
		T removed = this.start.value;
		
		// replace start
		this.start = this.start.next;
		
		// return removed value
		return removed;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		// special case for list with only 1 node
		if (this.start.next == null) {
			return removeFront();
		}
		
		// otherwise, go through list
		Node<T> secondLast = null;
		// find the second-to-last node
		for (secondLast = this.start; secondLast.next.next != null; secondLast = secondLast.next) {}
		// save last node's value
		T removed = secondLast.next.value;
		
		// make second-to-last node the last node
		secondLast.next = null;
		
		// return removed value
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
		// special case for removing front
		if (index == 0) {
			return removeFront();
		}
		
		// otherwise, find node before index and point to node after index
		// we're "at" the next index - so increment before comparing to index
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (++at == index) {
				T removed = n.next.value;
				n.next = n.next.next;
				return removed;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		// account for empty list
		if (isEmpty()) {
			addFront(item);
			return;
		}
		
		// otherwise, go through list
		Node<T> last = null;
		// find the node whose next is null
		for (last = this.start; last.next != null; last = last.next) {}
		// set its next to new back node
		last.next = new Node<T>(item);
	}

	@Override
	public void addIndex(int index, T item) {
		// account for empty list or adding to front
		if (isEmpty() || index == 0) {
			this.start = new Node<T>(item, start);
			return;
		}
		
		// otherwise, go through list and add at index
		// we're "at" the next index - so increment before comparing to index
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (++at == index) {
				n.next = new Node<T>(item, n.next);
				return;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		Node<T> last = null;
		// find the node whose next is null
		for (last = this.start; last.next != null; last = last.next) {}
		// returns the value of the node whose next is null
		return last.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
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
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with a friend.
		 * @param value - the value to put in it.
		 * @param next - the friend of this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Alternate constructor; create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.next = null;
		}
	}

}
