package autocomplete;

import java.util.LinkedList;
import java.util.List;

/**
 * The TST class represents a string set. It also provides character-based methods for
 * returning the key that is the longest prefix of a string and returning all keys that
 * start with a given prefix.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Kevin Lin
 */
public class TST {
    private int size;
    private Node overallRoot;

    private static class Node {
        private char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char curr) {
            this.data = curr;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }

    /** Returns the number of strings in this TST. */
    public int size() {
        return size;
    }

    /** Returns true if and only if this TST contains the given key. */
    public boolean contains(String key) {
        if (key == null) {
            throw new NullPointerException("calls contains() with null argument");
        } else if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node node = get(overallRoot, key, 0);
        return node != null && node.isTerm;
    }

    // Private helper method for returning the node (subtree) corresponding to given key
    private Node get(Node node, String key, int depth) {
        if (node == null) {
            return null;
        }
        char curr = key.charAt(depth);
        if (curr < node.data) {
            // if curr comes before node.data, traverse down the left subtree
            return get(node.left, key, depth);
        } else if (curr > node.data) {
            // if curr comes after node.data, traverse down the right subtree
            return get(node.right, key, depth);
        } else if (depth < key.length() - 1) {
            // if curr is the correct letter, and not at the end of the tree, traverse down the middle subtree
            return get(node.mid, key, depth + 1);
        } else {
            // if at the end of the tree path, return the current letter
            return node;
        }
    }

    /** Inserts the string into the TST. */
    public void add(String key) {
        if (key == null) {
            throw new NullPointerException("calls add() with null key");
        }
        if (!contains(key)) {
            overallRoot = add(overallRoot, key, 0);
            size += 1;
        }
    }

    // Private helper method for adding a node if it doesn't already exist in the TST.
    private Node add(Node node, String key, int depth) {
        char curr = key.charAt(depth);
        if (node == null) {
            node = new Node(curr);
        }
        if (curr < node.data) {
            // if curr comes alphabetically before node.data, traverse down the left subtree
            node.left = add(node.left, key, depth);
        } else if (curr > node.data) {
            // if curr comes alphabetically after node.data, traverse down the right subtree
            node.right = add(node.right, key, depth);
        } else if (depth < key.length() - 1) {
            // if curr is the correct letter, and not at the end of the tree, traverse down the middle subtree
            node.mid = add(node.mid, key, depth + 1);
        } else {
            // if at the end of the tree path, ensure isTerm is true
            node.isTerm = true;
        }
        return node;
    }

    /** Returns the string that is the longest prefix of the given query, or null if no such string. */
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new NullPointerException("calls longestPrefixOf() with null argument");
        } else if (query.length() == 0) {
            return null;
        }
        int length = 0;
        Node node = overallRoot;
        int i = 0;
        while (node != null && i < query.length()) {
            char curr = query.charAt(i);
            if (curr < node.data) {
                node = node.left;
            } else if (curr > node.data) {
                node = node.right;
            } else {
                i += 1;
                if (node.isTerm) {
                    length = i;
                }
                node = node.mid;
            }
        }
        return query.substring(0, length);
    }

    /** Returns all strings in the TST as a list. */
    public List<String> keys() {
        List<String> list = new LinkedList<>();
        collect(overallRoot, new StringBuilder(), list);
        return list;
    }

    /** Returns all of the keys in the set that start with the given prefix. */
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        List<String> list = new LinkedList<>();
        Node node = get(overallRoot, prefix, 0);
        if (node == null) {
            return list;
        }
        if (node.isTerm) {
            list.add(prefix);
        }
        collect(node.mid, new StringBuilder(prefix), list);
        return list;
    }

    // Private helper method for collecting all keys in the node (subtree) with the given prefix
    private void collect(Node node, StringBuilder prefix, List<String> list) {
        if (node == null) {
            return;
        }
        collect(node.left, prefix, list);
        if (node.isTerm) {
            list.add(prefix.toString() + node.data);
        }
        prefix.append(node.data);
        collect(node.mid, prefix, list);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(node.right, prefix, list);
    }

    public static void main(String[] args) {
        TST tst = new TST();
        tst.add("she");
        tst.add("sells");
        tst.add("sea");
        tst.add("shells");
        tst.add("by");
        tst.add("the");
        tst.add("sea");
        tst.add("shore");

        System.out.println("keys():");
        for (String s : tst.keys()) {
            System.out.println("    " + s);
        }
        System.out.println();

        System.out.println("longestPrefixOf(\"shellsort\"):");
        System.out.println("    " + tst.longestPrefixOf("shellsort"));
        System.out.println();

        System.out.println("longestPrefixOf(\"shell\"):");
        System.out.println("    " + tst.longestPrefixOf("shell"));
        System.out.println();

        System.out.println("keysWithPrefix(\"sh\"):");
        for (String s : tst.keysWithPrefix("sh")) {
            System.out.println("    " + s);
        }
        System.out.println();
    }
}