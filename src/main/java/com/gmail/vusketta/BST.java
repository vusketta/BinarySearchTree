package com.gmail.vusketta;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public BST(@NotNull E... keys) {
        for (E key : keys) insert(key);
    }

    public Node<E> search(final E key) {
        return search(root, key);
    }

    public void insert(final E key) {
        root = insert(root, null, key);
    }

    public void delete(final E key) {
        root = delete(root, key);
    }

    public E min() {
        return min(root).key;
    }

    public E max() {
        return max(root).key;
    }

    public E[] findNeighbours(final E key) {
        Node node = search(key);
        return (E[]) new Comparable[] {node.parent.key, node.left.key, node.right.key};
    }

    private Node<E> search(final Node<E> curr, final E key) {
        if (curr == null) return null;
        return switch (key.compareTo(curr.key)) {
            case 1 -> search(curr.right, key);
            case -1 -> search(curr.left, key);
            case 0 -> curr;
            default -> throw new IllegalStateException("Unexpected value: " + key.compareTo(curr.key));
        };
    }

    @NotNull
    @Contract("null, _, _ -> new")
    private Node<E> insert(final Node<E> curr, final Node<E> parent, final E key) {
        if (curr == null) return new Node<>(key, parent);
        switch (key.compareTo(curr.key)) {
            case 1 -> curr.right = insert(curr.right, curr, key);
            case -1 -> curr.left = insert(curr.left, curr, key);
            case 0 -> {
            }
        }
        return curr;
    }

    private Node<E> delete(Node<E> curr, final E key) {
        if (curr == null) return null;
        final int compare = key.compareTo(curr.key);
        if (compare == 1) {
            curr.right = delete(curr.right, key);
        } else if (compare == -1) {
            curr.left = delete(curr.left, key);
        } else if (curr.left != null && curr.right != null) {
            curr.key = min(curr.right).key;
            curr.right = delete(curr.right, curr.key);
        } else {
            if (curr.left != null) curr = curr.left;
            else if (curr.right != null) curr = curr.right;
            else curr = null;
        }
        return curr;
    }

    @NotNull
    @Contract(pure = true)
    private Node<E> min(@NotNull final Node<E> curr) {
        if (curr.left == null) return curr;
        return min(curr.left);
    }

    @NotNull
    @Contract(pure = true)
    private Node<E> max(@NotNull final Node<E> curr) {
        if (curr.right == null) return curr;
        return max(curr.right);
    }

    @Override
    public String toString() {
        return "BST(\n\t" + root + "\n)";

    }

    static class Node<T extends Comparable<T>> {
        private T key;
        private Node<T> left, right, parent;

        public Node(final T key, final Node<T> parent) {
            this.key = key;
            this.parent = parent;
        }

        @Override
        public String toString() {
            final String l = left != null ? left.toString() : "-";
            final String r = right != null ? right.toString() : "-";
            return "Node(" + key + ", " + l + ", " + r + ')';
        }
    }
}
